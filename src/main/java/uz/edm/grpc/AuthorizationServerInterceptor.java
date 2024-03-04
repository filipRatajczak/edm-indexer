package uz.edm.grpc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.edm.constants.Constants;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import static uz.edm.constants.Constants.CLAIM_EXP;
import static uz.edm.constants.Constants.INVOKED_GRPC_METHOD_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationServerInterceptor implements ServerInterceptor {

    private final KeyPair keyPair;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String value = metadata.get(Constants.AUTHORIZATION);

        if (serverCall.getMethodDescriptor().getBareMethodName().equals(INVOKED_GRPC_METHOD_NAME)) {
            return Contexts.interceptCall(Context.current(), serverCall, metadata, serverCallHandler);
        }

        Status status;
        if (value == null) {
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!value.startsWith(Constants.BEARER)) {
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
            String token = value.substring(Constants.BEARER.length()).trim();

            DecodedJWT verify = JWT.require(algorithm).build().verify(token);

            Claim claim = verify.getClaim(CLAIM_EXP);

            boolean isAfter = claim.asInstant().isBefore(Instant.now());

            if (isAfter) {
                status = Status.UNAUTHENTICATED.withDescription("Token expired.");
            } else {
                log.info("Token ok. Can proceed the request.");
                return Contexts.interceptCall(Context.current(), serverCall, metadata, serverCallHandler);
            }

        }

        serverCall.close(status, metadata);
        return new ServerCall.Listener<>() {
        };
    }
}
