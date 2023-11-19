package uz.edm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.edm.exception.EmployeeNotFoundException;
import uz.edm.model.Employee;
import uz.edm.model.dto.EmployeeDto;
import uz.edm.model.dto.EmployeeViewDto;
import uz.edm.repository.EmployeeRepository;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final KeyPair keyPair;
    private final PasswordEncoder passwordEncoder;

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
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return Optional.of(employeeRepository.save(employee))
                .map(Employee::mapToViewDto)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteDisposition(UUID id) {
        employeeRepository.deleteById(id);
    }

    public String getJwtToken(String email, String password) {
        Optional<Employee> employee = employeeRepository.getByEmail(email);
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
        return employee.map(e -> {
            if (passwordEncoder.matches(password, e.getPassword())) {
                return JWT.create()
                        .withIssuer(e.getEmail())
                        .withExpiresAt(Instant.now().plus(365, TimeUnit.DAYS.toChronoUnit()))
                        .sign(algorithm);
            } else {
                return "";
            }
        }).orElse("");

    }

    public EmployeeDto getByEmail(String email) {
        return employeeRepository.getByEmail(email).map(Employee::mapToDto)
                .orElse(null);
    }


}
