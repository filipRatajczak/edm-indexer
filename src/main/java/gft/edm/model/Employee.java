package gft.edm.model;

import gft.edm.model.dto.EmployeeDto;
import gft.edm.model.dto.EmployeeViewDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private UUID id = UUID.randomUUID();
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
    @Size(min = 8, max = 20)
    @NotNull
    @NotEmpty
    @NotBlank
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

    public static Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setAddress(employeeDto.getAddress());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setEmployeeCode(employeeDto.getEmployeeCode());
        return employee;
    }

}
