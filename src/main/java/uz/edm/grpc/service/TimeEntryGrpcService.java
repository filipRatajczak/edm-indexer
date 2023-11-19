package uz.edm.grpc.service;

import com.google.protobuf.Empty;
import uz.edm.grpc.timeentry.CreateTimeEntryRequest;
import uz.edm.grpc.timeentry.DeleteTimeEntryRequest;
import uz.edm.grpc.timeentry.GetTimeEntryByEmployeeCodeRequest;
import uz.edm.grpc.timeentry.TimeEntries;
import uz.edm.grpc.timeentry.TimeEntry;
import uz.edm.grpc.timeentry.TimeEntryServiceGrpc;
import uz.edm.grpc.timeentry.UpdateTimeEntryRequest;
import uz.edm.model.dto.TimeEntryDto;
import uz.edm.service.TimeEntryService;
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
public class TimeEntryGrpcService extends TimeEntryServiceGrpc.TimeEntryServiceImplBase {

    private final TimeEntryService timeEntryService;

    @Override
    public void getTimeEntryByEmployeeCode(GetTimeEntryByEmployeeCodeRequest request, StreamObserver<TimeEntries> responseObserver) {
        List<TimeEntryDto> timeEntryDtos = timeEntryService.getTimeEntriesByEmployeeCode(request.getEmployeeCode(), parseStringToLocalDate(request.getStart()), parseStringToLocalDate(request.getStop()));
        responseObserver.onNext(timeEntriesToGrpcFormat(timeEntryDtos));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTimeEntries(Empty request, StreamObserver<TimeEntries> responseObserver) {
        List<TimeEntryDto> dispositionDtos = timeEntryService.getAllTimeEntries();
        responseObserver.onNext(timeEntriesToGrpcFormat(dispositionDtos));
        responseObserver.onCompleted();
    }

    @Override
    public void updateTimeEntry(UpdateTimeEntryRequest request, StreamObserver<TimeEntry> responseObserver) {
        TimeEntryDto timeEntryDto = timeEntryService.updateTimeEntry(request.getEmployeeCode(), createTimeEntryFromGrpcFormat(request.getTimeEntry()));
        responseObserver.onNext(timeEntryToGrpcFormat(timeEntryDto));
        responseObserver.onCompleted();
    }

    @Override
    public void createTimeEntry(CreateTimeEntryRequest request, StreamObserver<TimeEntry> responseObserver) {
        TimeEntryDto timeEntryDto = timeEntryService.createTimeEntry(createTimeEntryFromGrpcFormat(request.getTimeEntry()));
        responseObserver.onNext(timeEntryToGrpcFormat(timeEntryDto));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTimeEntry(DeleteTimeEntryRequest request, StreamObserver<Empty> responseObserver) {
        timeEntryService.deleteTimeEntry(UUID.fromString(request.getId()));
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    private TimeEntries timeEntriesToGrpcFormat(List<TimeEntryDto> timeEntryDtos) {
        List<TimeEntry> collect = timeEntryDtos.stream()
                .map(this::timeEntryToGrpcFormat)
                .toList();
        return TimeEntries.newBuilder().addAllTimeEntry(collect).build();
    }

    private TimeEntry timeEntryToGrpcFormat(TimeEntryDto timeEntryDto) {
        return TimeEntry.newBuilder()
                .setDay(timeEntryDto.getDay().toString())
                .setStart(timeEntryDto.getStart())
                .setStop(timeEntryDto.getStop())
                .setEmployeeCode(timeEntryDto.getEmployeeCode())
                .build();
    }

    private TimeEntryDto createTimeEntryFromGrpcFormat(TimeEntry timeEntry) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(timeEntry.getDay());
        TimeEntryDto timeEntryDto = new TimeEntryDto();
        timeEntryDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        timeEntryDto.setStart(timeEntry.getStart());
        timeEntryDto.setStart(timeEntry.getStop());
        timeEntryDto.setEmployeeCode(timeEntry.getEmployeeCode());
        return timeEntryDto;
    }

    private java.time.LocalDate parseStringToLocalDate(String date) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(date);
        return java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
    }

}
