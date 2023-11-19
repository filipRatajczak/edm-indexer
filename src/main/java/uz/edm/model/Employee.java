package uz.edm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uz.edm.model.dto.EmployeeDto;
import uz.edm.model.dto.EmployeeViewDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private UUID id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String lastName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String address;
    @Size(min = 9, max = 9)
    @NotNull
    @NotEmpty
    @NotBlank
    private String phoneNumber;
    @Column(unique = true)
    @Email
    private String email;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull @NotEmpty @NotBlank
    private String password;
    @Column(unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String employeeCode;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disposition> dispositions = new ArrayList<>();
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntry> timeEntries = new ArrayList<>();


    public static EmployeeViewDto mapToViewDto(Employee employee) {
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
        return employeeViewDto;
    }

    public static EmployeeDto mapToDto(Employee employee) {
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
        return employeeDto;
    }

    public static Employee toEntity(EmployeeDto employeeDto) {
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
        return employee;
    }

}
