package gft.edm.grpc;

import gft.edm.grpc.service.DispositionGrpcService;
import gft.edm.grpc.service.EmployeeGrpcService;
import gft.edm.grpc.service.TimeEntryGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class GrpcServer {

    private final DispositionGrpcService dispositionGrpcService;
    private final TimeEntryGrpcService timeEntryGrpcService;
    private final EmployeeGrpcService employeeGrpcService;

    @PostConstruct
    public void createGrpcServer() throws IOException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(dispositionGrpcService)
                .addService(timeEntryGrpcService)
                .addService(employeeGrpcService)
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


