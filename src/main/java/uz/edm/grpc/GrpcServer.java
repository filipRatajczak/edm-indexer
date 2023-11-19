package uz.edm.grpc;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.edm.grpc.service.AuthenticationGrpcService;
import uz.edm.grpc.service.DispositionGrpcService;
import uz.edm.grpc.service.EmployeeGrpcService;
import uz.edm.grpc.service.TimeEntryGrpcService;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class GrpcServer {

    private final DispositionGrpcService dispositionGrpcService;
    private final TimeEntryGrpcService timeEntryGrpcService;
    private final EmployeeGrpcService employeeGrpcService;
    private final AuthenticationGrpcService authenticationGrpcService;
    private final AuthorizationServerInterceptor authorizationServerInterceptor;

    @PostConstruct
    public void createGrpcServer() throws IOException {

        Server server = ServerBuilder
                .forPort(8136)
                .intercept(authorizationServerInterceptor)
                .addService(dispositionGrpcService)
                .addService(timeEntryGrpcService)
                .addService(employeeGrpcService)
                .addService(authenticationGrpcService)
                .build();

        server.start();
        new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                log.error("Exception occurred during awaitTermination()", e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }


}


