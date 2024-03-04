package uz.edm.grpc.service;

import lombok.extern.slf4j.Slf4j;
import uz.edm.grpc.authentication.AuthenticationServiceGrpc;
import uz.edm.grpc.authentication.LoginRequest;
import uz.edm.grpc.authentication.LoginResponse;
import uz.edm.service.EmployeeService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationGrpcService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    private final EmployeeService employeeService;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.info("Processing token request for email: [{}]", request.getEmail());
        String jwtToken = employeeService.getJwtToken(request.getEmail(), request.getPassword());
        responseObserver.onNext(LoginResponse.newBuilder().setToken(jwtToken).build());
        responseObserver.onCompleted();
    }
}
