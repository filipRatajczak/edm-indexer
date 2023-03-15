package gft.edm.service;

import gft.edm.model.TimeEntry;
import gft.edm.model.dto.TimeEntryDto;
import gft.edm.repository.TimeEntryRepository;
import gft.edm.validation.TimeEntryValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryValidation timeEntryValidation;

    public List<TimeEntryDto> getTimeEntriesByEmployeeCode(String employeeCode) {
        return timeEntryRepository.getAllByEmployeeCode(employeeCode).stream()
                .map(TimeEntry::toDto)
                .collect(Collectors.toList());
    }

    public List<TimeEntryDto> getAllTimeEntries() {
        return timeEntryRepository.getAll().stream()
                .map(TimeEntry::toDto)
                .collect(Collectors.toList());
    }

    public TimeEntryDto updateTimeEntry(TimeEntryDto timeEntryDto) {

        TimeEntryDto checkedTimeEntry = timeEntryValidation.validateTimeEntry(timeEntryDto);

        return timeEntryRepository.updateDisposition(checkedTimeEntry)
                .map(TimeEntry::toDto)
                .orElseThrow(() -> new RuntimeException("Disposition with id: " + timeEntryDto.getEmployeeCode() + " does not found"));
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
