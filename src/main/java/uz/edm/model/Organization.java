package uz.edm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Organization {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(unique = true)
    private String organizationCode;

    @ManyToMany(mappedBy = "organizations", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Disposition> dispositions;


    public Organization(String name, String organizationCode, Set<Employee> employees, Employee employee, Set<Disposition> dispositions) {
        this.name = name;
        this.organizationCode = organizationCode;
        this.employees = employees;
        this.employee = employee;
        this.dispositions = dispositions;
    }
}
