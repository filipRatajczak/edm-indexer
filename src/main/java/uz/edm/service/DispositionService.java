package uz.edm.service;

import uz.edm.model.Disposition;
import uz.edm.model.TimeEntry;
import uz.edm.model.dto.DispositionDto;
import uz.edm.model.dto.DispositionRatioDto;
import uz.edm.repository.DispositionRepository;
import uz.edm.repository.TimeEntryRepository;
import uz.edm.util.DateUtils;
import uz.edm.validation.DispositionValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class DispositionService {

    private final DispositionRepository dispositionRepository;
    private final DispositionValidation dispositionValidation;
    private final TimeEntryRepository timeEntryRepository;

    public List<DispositionDto> getDispositionByEmployeeCode(String employeeCode, LocalDate start, LocalDate stop) {
        return dispositionRepository.getAllByEmployeeCode(employeeCode, start.toString(), stop.toString()).stream()
                .map(Disposition::toDto)
                .toList();
    }

    public List<DispositionDto> getAllDisposition(LocalDate from, LocalDate to) {
        return dispositionRepository.getAll(from, to).stream()
                .map(Disposition::toDto)
                .toList();
    }

    public DispositionRatioDto getDispositionRatioDto(String employeeCode) {
        List<Disposition> dispositions = dispositionRepository.getAllByEmployeeCodeInTimePeriod(employeeCode, LocalDate.now().minusMonths(1), LocalDate.now());
        List<TimeEntry> timeEntries = timeEntryRepository.getAllByEmployeeCodeInTimePeriod(employeeCode, LocalDate.now().minusMonths(1), LocalDate.now());

        BigDecimal sumOfDispositionHours = BigDecimal.valueOf(dispositions.stream().map(e -> DateUtils.getDifferenceInHours(e.getStart(), e.getStop())).count());
        BigDecimal sumOfTimeEntryHours = BigDecimal.valueOf(timeEntries.stream().map(e -> DateUtils.getDifferenceInHours(e.getStart(), e.getStop())).count());

        if (sumOfDispositionHours.equals(BigDecimal.ZERO)) {
            return new DispositionRatioDto(employeeCode, BigDecimal.ZERO);
        }

        return new DispositionRatioDto(employeeCode, sumOfTimeEntryHours.divide(sumOfDispositionHours).movePointLeft(2));
    }

    public DispositionDto updateDisposition(String employeeCode, DispositionDto dispositionDto) {
        DispositionDto checkedDisposition = dispositionValidation.validateDisposition(dispositionDto);
        return dispositionRepository.updateDisposition(employeeCode, checkedDisposition)
                .map(Disposition::toDto)
                .orElseThrow(() -> new RuntimeException("Disposition with id: " + dispositionDto.getEmployeeCode() + " does not found"));
    }

    public DispositionDto createDisposition(DispositionDto dispositionDto) {
        DispositionDto checkedDisposition = dispositionValidation.validateDisposition(dispositionDto);
        Disposition disposition = Disposition.toEntity(checkedDisposition);
        return Optional.of(dispositionRepository.save(disposition))
                .map(Disposition::toDto)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteDisposition(UUID id) {
        dispositionRepository.deleteById(id);
    }


}