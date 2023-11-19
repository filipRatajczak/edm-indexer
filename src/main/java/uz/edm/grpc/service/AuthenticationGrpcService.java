package uz.edm.grpc.service;

import uz.edm.grpc.authentication.AuthenticationServiceGrpc;
import uz.edm.grpc.authentication.LoginRequest;
import uz.edm.grpc.authentication.LoginResponse;
import uz.edm.service.EmployeeService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationGrpcService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    private final EmployeeService employeeService;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String jwtToken = employeeService.getJwtToken(request.getEmail(), request.getPassword());
        responseObserver.onNext(LoginResponse.newBuilder().setToken(jwtToken).build());
        responseObserver.onCompleted();
    }
}
