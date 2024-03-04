package uz.edm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
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
    @ManyToOne
    private Organization organization;
    private LocalDate day;
    private String start;
    private String stop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disposition that = (Disposition) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(employee, that.employee)) return false;
        if (!Objects.equals(organization, that.organization)) return false;
        if (!Objects.equals(day, that.day)) return false;
        if (!Objects.equals(start, that.start)) return false;
        return Objects.equals(stop, that.stop);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (stop != null ? stop.hashCode() : 0);
        return result;
    }
}
