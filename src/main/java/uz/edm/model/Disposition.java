package uz.edm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.edm.model.dto.DispositionDto;

import java.time.LocalDate;
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
    private String start;
    private String stop;
    private String employeeCode;

    public static DispositionDto toDto(Disposition disposition) {
        DispositionDto dto = new DispositionDto();
        dto.setId(disposition.getId());
        dto.setDay(disposition.getDay());
        dto.setStart(disposition.getStart());
        dto.setStop(disposition.getStop());
        dto.setEmployeeCode(disposition.getEmployeeCode());
        return dto;
    }

    public static Disposition toEntity(DispositionDto dispositionDto) {
        Disposition disposition = new Disposition();
        disposition.day = dispositionDto.getDay();
        disposition.start = dispositionDto.getStart();
        disposition.stop = dispositionDto.getStop();
        disposition.employeeCode = dispositionDto.getEmployeeCode();
        return disposition;
    }

    @Override
    public String toString() {
        return "Disposition{" +
                "id=" + id +
                ", employee=" + employee +
                ", day=" + day +
                ", start='" + start + '\'' +
                ", stop='" + stop + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }
}
