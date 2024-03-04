package uz.edm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final KeyPair keyPair;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationService organizationService;

    public List<EmployeeViewDto> getAllEmployees() {
        return employeeRepository.getAll().stream()
                .map(Employee::mapToViewDto)
                .toList();
    }

    public EmployeeViewDto getEmployeeViewByEmployeeCode(String employeeCode) {
        return employeeRepository.getByEmployeeCode(employeeCode)
                .map(Employee::mapToViewDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employeeCode: [" + employeeCode + "] does not found."));
    }

    public Employee getEmployeeByEmployeeCode(String employeeCode) {
        return employeeRepository.getByEmployeeCode(employeeCode)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employeeCode: [" + employeeCode + "] does not found."));
    }

    public EmployeeViewDto updateEmployee(String employeeCode, EmployeeDto employeeDto) {
        return employeeRepository.updateEmployee(employeeCode, employeeDto)
                .map(Employee::mapToViewDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employeeCode: [" + employeeCode + "] does not found."));
    }

    public EmployeeViewDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = toEntity(employeeDto);
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
        return employeeRepository.getByEmail(email).map(this::mapToDto)
                .orElse(null);
    }

    public EmployeeViewDto mapToViewDto(Employee employee) {
        EmployeeViewDto employeeViewDto = new EmployeeViewDto();
        employeeViewDto.setId(employee.getId());
        employeeViewDto.setFirstName(employee.getFirstName());
        employeeViewDto.setLastName(employee.getLastName());
        employeeViewDto.setAddress(employee.getAddress());
        employeeViewDto.setPhoneNumber(employee.getPhoneNumber());
        employeeViewDto.setEmail(employee.getEmail());
        employeeViewDto.setBirthday(employee.getBirthday());
        employeeViewDto.setRole(employee.getRole());
        employeeViewDto.setEmployeeCode(employee.getEmployeeCode());
        employeeViewDto.setOrganizationCodes(organizationService.mapOrganizationsToOrganizationCodes(employee.getOrganizations()));
        employeeViewDto.setOwnedOrganizations(organizationService.mapOrganizationsToOrganizationCodes(employee.getOwnedOrganizations()));
        return employeeViewDto;
    }



    public EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setBirthday(employee.getBirthday());
        employeeDto.setRole(employee.getRole());
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setPassword(employee.getPassword());
        employeeDto.setOrganizationCodes(organizationService.mapOrganizationsToOrganizationCodes(employee.getOrganizations()));
        employeeDto.setOwnedOrganizations(organizationService.mapOrganizationsToOrganizationCodes(employee.getOwnedOrganizations()));
        return employeeDto;
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setAddress(employeeDto.getAddress());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setEmployeeCode(employeeDto.getEmployeeCode());
        employee.setBirthday(employeeDto.getBirthday());
        employee.setRole(employeeDto.getRole());
        employee.setPassword(employeeDto.getPassword());
        employee.setOrganizations(organizationService.getAllOrganizationsByOrganizationCodes(employeeDto.getOrganizationCodes()));
        return employee;
    }


}
