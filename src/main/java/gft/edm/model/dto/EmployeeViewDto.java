package gft.edm.model.dto;

import gft.edm.model.Role;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeViewDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate birthday;
    private Role role;
    private String employeeCode;

}
