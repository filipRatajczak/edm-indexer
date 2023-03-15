package gft.edm.model;


import gft.edm.model.dto.TimeEntryDto;
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
public class TimeEntry {

    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne
    private Employee employee;
    private LocalDate day;
    private String start;
    private String stop;
    private String employeeCode;


    public static TimeEntryDto toDto(TimeEntry timeEntry) {
        TimeEntryDto dto = new TimeEntryDto();
        dto.setId(timeEntry.getId());
        dto.setDay(timeEntry.getDay());
        dto.setStart(timeEntry.getStart());
        dto.setStop(timeEntry.getStop());
        dto.setEmployeeCode(timeEntry.getEmployeeCode());
        return dto;
    }

    public static TimeEntry toEntity(TimeEntryDto timeEntryDto) {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.day = timeEntryDto.getDay();
        timeEntry.start = timeEntryDto.getStart();
        timeEntry.stop = timeEntryDto.getStop();
        timeEntry.employeeCode = timeEntryDto.getEmployeeCode();
        return timeEntry;
    }

}
