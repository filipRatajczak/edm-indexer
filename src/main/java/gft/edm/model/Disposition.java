package gft.edm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disposition {

    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne
    private Employee employee;
    private LocalDate day;
    private LocalDateTime start;
    private LocalDateTime stop;
    private String employeeCode;

}
