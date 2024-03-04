package uz.edm.grpc.service;

import com.google.protobuf.Empty;
import uz.edm.grpc.employee.CreateEmployeeRequest;
import uz.edm.grpc.employee.DeleteEmployeeRequest;
import uz.edm.grpc.employee.Employee;
import uz.edm.grpc.employee.EmployeeServiceGrpc;
import uz.edm.grpc.employee.Employees;
import uz.edm.grpc.employee.GetEmployeeByEmailRequest;
import uz.edm.grpc.employee.GetEmployeeByEmployeeCodeRequest;
import uz.edm.grpc.employee.UpdateEmployeeRequest;
import uz.edm.model.Role;
import uz.edm.model.dto.EmployeeDto;
import uz.edm.model.dto.EmployeeViewDto;
import uz.edm.service.EmployeeService;
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
    public void geteEmployeeByEmail(GetEmployeeByEmailRequest request, StreamObserver<Employee> responseObserver) {
        EmployeeDto employeeDto = employeeService.getByEmail(request.getEmail());
        responseObserver.onNext(employeeDtoToGrpcFormat(employeeDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployeeByEmployeeCode(GetEmployeeByEmployeeCodeRequest request, StreamObserver<Employee> responseObserver) {
        EmployeeViewDto employeeViewDto = employeeService.getEmployeeViewByEmployeeCode(request.getEmployeeCode());
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
        EmployeeViewDto employeeViewDto = employeeService.updateEmployee(request.getEmployeeCode(), createEmployeeFromGrpcFormat(request.getEmployee()));
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
    private Employee employeeDtoToGrpcFormat(EmployeeDto employeeDto) {
        return Employee.newBuilder()
                .setFirstName(employeeDto.getFirstName())
                .setLastName(employeeDto.getLastName())
                .setAddress(employeeDto.getAddress())
                .setBirthday(employeeDto.getBirthday().toString())
                .setEmail(employeeDto.getEmail())
                .setEmployeeCode(employeeDto.getEmployeeCode())
                .setPhoneNumber(employeeDto.getPhoneNumber())
                .setRole(employeeDto.getRole().name())
                .setPassword(employeeDto.getPassword())
                .build();
    }

    private EmployeeDto createEmployeeFromGrpcFormat(Employee employee) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate localDate = timeFormatter.parseLocalDate(employee.getBirthday());
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setBirthday(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setRole(Role.valueOf(employee.getRole()));
        employeeDto.setPassword(employee.getPassword());
        return employeeDto;
    }

}
