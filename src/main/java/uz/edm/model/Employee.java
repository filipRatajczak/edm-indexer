package uz.edm.model;

import com.google.common.base.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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
    private Set<Disposition> dispositions = new HashSet<>();
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TimeEntry> timeEntries = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_organization",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    Set<Organization> organizations;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Organization> ownedOrganizations = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equal(id, employee.id) && Objects.equal(firstName, employee.firstName) && Objects.equal(lastName, employee.lastName) && Objects.equal(address, employee.address) && Objects.equal(phoneNumber, employee.phoneNumber) && Objects.equal(email, employee.email) && Objects.equal(birthday, employee.birthday) && role == employee.role && Objects.equal(password, employee.password) && Objects.equal(employeeCode, employee.employeeCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName, address, phoneNumber, email, birthday, role, password, employeeCode);
    }
}
