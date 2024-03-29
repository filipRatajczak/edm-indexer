package uz.edm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeEntryDto {

    private UUID id;
    private LocalDate day;
    private String start;
    private String stop;
    private String employeeCode;

    public TimeEntryDto(LocalDate day, String start, String stop, String employeeCode) {
        this.day = day;
        this.start = start;
        this.stop = stop;
        this.employeeCode = employeeCode;
    }


}
