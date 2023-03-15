package gft.edm.model;

import gft.edm.model.dto.DispositionDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
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
        disposition.start = disposition.getStart();
        disposition.stop = disposition.getStop();
        disposition.employeeCode = dispositionDto.getEmployeeCode();
        return disposition;
    }

}
