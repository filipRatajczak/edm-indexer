package uz.edm.model.dto;

import uz.edm.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate birthday;
    private Role role;
    private String password;
    private String employeeCode;

    public EmployeeDto(String firstName, String lastName, String address, String phoneNumber, String email, LocalDate birthday, Role role, String password, String employeeCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.password = password;
        this.employeeCode = employeeCode;
    }
}
