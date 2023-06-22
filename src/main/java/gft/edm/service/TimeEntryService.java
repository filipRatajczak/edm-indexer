package gft.edm.service;

import gft.edm.exception.EmployeeNotFoundException;
import gft.edm.exception.TimeEntryNotFoundException;
import gft.edm.model.TimeEntry;
import gft.edm.model.dto.TimeEntryDto;
import gft.edm.repository.TimeEntryRepository;
import gft.edm.validation.TimeEntryValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryValidation timeEntryValidation;

    public List<TimeEntryDto> getTimeEntriesByEmployeeCode(String employeeCode, LocalDate start, LocalDate stop) {
        return timeEntryRepository.getAllByEmployeeCode(employeeCode, start, stop).stream()
                .map(TimeEntry::toDto)
                .toList();
    }

    public List<TimeEntryDto> getAllTimeEntries() {
        return timeEntryRepository.getAll().stream()
                .map(TimeEntry::toDto)
                .toList();
    }

    public TimeEntryDto updateTimeEntry(String employeeCode, TimeEntryDto timeEntryDto) {

        TimeEntryDto checkedTimeEntry = timeEntryValidation.validateTimeEntry(timeEntryDto);

        return timeEntryRepository.updateDisposition(employeeCode, checkedTimeEntry)
                .map(TimeEntry::toDto)
                .orElseThrow(() -> new TimeEntryNotFoundException("TimeEntry with employeeCode: [" + employeeCode + "] does not found."));
    }

    public TimeEntryDto createDisposition(TimeEntryDto timeEntryDto) {
        TimeEntryDto checkedTimeEntry = timeEntryValidation.validateTimeEntry(timeEntryDto);
        TimeEntry disposition = TimeEntry.toEntity(checkedTimeEntry);
        return Optional.of(timeEntryRepository.save(disposition))
                .map(TimeEntry::toDto)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteTimeEntry(UUID id) {
        timeEntryRepository.deleteById(id);
    }

}
