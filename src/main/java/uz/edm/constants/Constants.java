package uz.edm.constants;

import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constants {
    public static final String BEARER = "Bearer";
    public static final Metadata.Key<String> AUTHORIZATION = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);

    public static final String CLAIM_EXP = "exp";

    public static final String INVOKED_GRPC_METHOD_NAME = "Login";

}