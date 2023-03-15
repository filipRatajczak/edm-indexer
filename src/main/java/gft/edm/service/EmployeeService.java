package gft.edm.service;

import gft.edm.model.Employee;
import gft.edm.model.dto.EmployeeDto;
import gft.edm.model.dto.EmployeeViewDto;
import gft.edm.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeViewDto> getAllEmployees() {
        return employeeRepository.getAll().stream()
                .map(Employee::mapToViewDto)
                .collect(Collectors.toList());
    }

    public EmployeeViewDto getEmployeeByEmployeeCode(String employeeCode) {
        return Employee.mapToViewDto(employeeRepository.getByEmployeeCode(employeeCode));
    }

    public EmployeeViewDto updateEmployee(EmployeeDto employeeDto) {
        return employeeRepository.updateEmployee(employeeDto)
                .map(Employee::mapToViewDto)
                .orElseThrow(RuntimeException::new);
    }

    public EmployeeViewDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = Employee.toEntity(employeeDto);
        return Optional.of(employeeRepository.save(employee))
                .map(Employee::mapToViewDto)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteDisposition(UUID id) {
        employeeRepository.deleteById(id);
    }

}
