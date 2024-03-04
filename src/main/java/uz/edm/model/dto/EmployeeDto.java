package uz.edm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uz.edm.model.Role;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    private Set<String> organizationCodes;
    private Set<String> ownedOrganizations;


    public EmployeeDto(UUID id, String firstName, String lastName, String address, String phoneNumber, String email, LocalDate birthday, Role role, String password, String employeeCode, Set<String> organizationCodes, Set<String> ownedOrganizations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.password = password;
        this.employeeCode = employeeCode;
        this.organizationCodes = organizationCodes;
        this.ownedOrganizations = ownedOrganizations;
    }
}
