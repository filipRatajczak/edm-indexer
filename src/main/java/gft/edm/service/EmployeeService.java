package gft.edm.service;

import gft.edm.exception.EmployeeNotFoundException;
import gft.edm.model.Employee;
import gft.edm.model.dto.EmployeeDto;
import gft.edm.model.dto.EmployeeViewDto;
import gft.edm.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeViewDto> getAllEmployees() {
        return employeeRepository.getAll().stream()
                .map(Employee::mapToViewDto)
                .toList();
    }

    public EmployeeViewDto getEmployeeByEmployeeCode(String employeeCode) {
        return employeeRepository.getByEmployeeCode(employeeCode)
                .map(Employee::mapToViewDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employeeCode: [" + employeeCode + "] does not found."));
    }

    public EmployeeViewDto updateEmployee(String employeeCode, EmployeeDto employeeDto) {
        return employeeRepository.updateEmployee(employeeCode, employeeDto)
                .map(Employee::mapToViewDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employeeCode: [" + employeeCode + "] does not found."));
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
