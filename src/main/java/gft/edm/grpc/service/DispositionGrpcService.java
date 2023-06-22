package gft.edm.grpc.service;

import com.google.protobuf.Empty;
import gft.edm.grpc.disposition.*;
import gft.edm.model.dto.DispositionDto;
import gft.edm.service.DispositionService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DispositionGrpcService extends DispositionServiceGrpc.DispositionServiceImplBase {

    private final DispositionService dispositionService;

    @Override
    public void getDispositionsByEmployeeCode(GetDispositionsByEmployeeCodeRequest request, StreamObserver<Dispositions> responseObserver) {
        List<DispositionDto> dispositionDtos = dispositionService.getDispositionByEmployeeCode(request.getEmployeeCode(), parseStringToLocalDate(request.getStart()), parseStringToLocalDate(request.getStop()));
        responseObserver.onNext(dispositionsToGrpcFormat(dispositionDtos));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllDisposition(Empty request, StreamObserver<Dispositions> responseObserver) {
        List<DispositionDto> dispositionDtos = dispositionService.getAllDisposition();
        responseObserver.onNext(dispositionsToGrpcFormat(dispositionDtos));
        responseObserver.onCompleted();
    }

    @Override
    public void updateDisposition(UpdateDispositionRequest request, StreamObserver<Disposition> responseObserver) {
        DispositionDto dispositionDto = dispositionService.updateDisposition(request.getEmployeeCode(), createDispositionFromGrpcFormat(request.getDisposition()));
        responseObserver.onNext(dispositionToGrpcFormat(dispositionDto));
        responseObserver.onCompleted();
    }

    @Override
    public void createDisposition(CreateDispositionRequest request, StreamObserver<Disposition> responseObserver) {
        DispositionDto dispositionDto = dispositionService.createDisposition(createDispositionFromGrpcFormat(request.getDisposition()));
        responseObserver.onNext(dispositionToGrpcFormat(dispositionDto));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteDisposition(DeleteDispositionsByIdRequest request, StreamObserver<Empty> responseObserver) {
        dispositionService.deleteDisposition(UUID.fromString(request.getId()));
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    private Dispositions dispositionsToGrpcFormat(List<DispositionDto> dispositionDtos) {
        List<Disposition> collect = dispositionDtos.stream()
                .map(this::dispositionToGrpcFormat)
                .toList();
        return Dispositions.newBuilder().addAllDisposition(collect).build();
    }

    private Disposition dispositionToGrpcFormat(DispositionDto dispositionDto) {
        return Disposition.newBuilder()
                .setDay(dispositionDto.getDay().toString())
                .setStart(dispositionDto.getStart())
                .setStop(dispositionDto.getStop())
                .setEmployeeCode(dispositionDto.getEmployeeCode())
                .build();
    }

    private DispositionDto createDispositionFromGrpcFormat(Disposition disposition) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(disposition.getDay());
        DispositionDto dispositionDto = new DispositionDto();
        dispositionDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        dispositionDto.setStart(disposition.getStart());
        dispositionDto.setStart(disposition.getStop());
        dispositionDto.setEmployeeCode(disposition.getEmployeeCode());
        return dispositionDto;
    }

    private java.time.LocalDate parseStringToLocalDate(String date) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(date);
        return java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
    }

}
