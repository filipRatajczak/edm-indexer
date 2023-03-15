package gft.edm.grpc.service;

import com.google.protobuf.Empty;
import gft.edm.grpc.disposition.*;
import gft.edm.model.Role;
import gft.edm.model.dto.EmployeeDto;
import gft.edm.model.dto.EmployeeViewDto;
import gft.edm.service.EmployeeService;
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
public class EmployeeGrpcService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    private final EmployeeService employeeService;

    @Override
    public void getEmployeeByEmployeeCode(GetEmployeeByEmployeeCodeRequest request, StreamObserver<Employee> responseObserver) {
        EmployeeViewDto employeeViewDto = employeeService.getEmployeeByEmployeeCode(request.getEmployeeCode());
        responseObserver.onNext(employeeViewToGrpcFormat(employeeViewDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllEmployees(Empty request, StreamObserver<Employees> responseObserver) {
        List<EmployeeViewDto> employeeViewDtos = employeeService.getAllEmployees();
        responseObserver.onNext(employeeViewsToGrpcFormat(employeeViewDtos));
        responseObserver.onCompleted();
    }

    @Override
    public void updateEmployee(UpdateEmployeeRequest request, StreamObserver<Employee> responseObserver) {
        EmployeeViewDto employeeViewDto = employeeService.updateEmployee(createEmployeeFromGrpcFormat(request.getEmployee()));
        responseObserver.onNext(employeeViewToGrpcFormat(employeeViewDto));
        responseObserver.onCompleted();
    }

    @Override
    public void createEmployee(CreateEmployeeRequest request, StreamObserver<Employee> responseObserver) {
        EmployeeViewDto employeeViewDto = employeeService.createEmployee(createEmployeeFromGrpcFormat(request.getEmployee()));
        responseObserver.onNext(employeeViewToGrpcFormat(employeeViewDto));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmployee(DeleteEmployeeRequest request, StreamObserver<Empty> responseObserver) {
        employeeService.deleteDisposition(UUID.fromString(request.getId()));
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    private Employees employeeViewsToGrpcFormat(List<EmployeeViewDto> employeeDtos) {
        List<Employee> collect = employeeDtos.stream()
                .map(this::employeeViewToGrpcFormat)
                .toList();
        return Employees.newBuilder().addAllEmployee(collect).build();
    }

    private Employee employeeViewToGrpcFormat(EmployeeViewDto employeeViewDto) {
        return Employee.newBuilder()
                .setFirstName(employeeViewDto.getFirstName())
                .setLastName(employeeViewDto.getLastName())
                .setAddress(employeeViewDto.getAddress())
                .setBirthday(employeeViewDto.getBirthday().toString())
                .setEmail(employeeViewDto.getEmail())
                .setEmployeeCode(employeeViewDto.getEmployeeCode())
                .setPhoneNumber(employeeViewDto.getPhoneNumber())
                .setRole(employeeViewDto.getRole().name())
                .setPassword("")
                .build();
    }

    private EmployeeDto createEmployeeFromGrpcFormat(Employee employee) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(employee.getBirthday());
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setBirthday(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setPhoneNumber(employeeDto.getPhoneNumber());
        employeeDto.setRole(Role.valueOf(employee.getRole()));
        employeeDto.setPassword(employee.getPassword());
        return employeeDto;
    }

}
