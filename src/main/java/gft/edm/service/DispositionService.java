package gft.edm.service;

import gft.edm.model.Disposition;
import gft.edm.model.dto.DispositionDto;
import gft.edm.repository.DispositionRepository;
import gft.edm.validation.DispositionValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DispositionService {

    private final DispositionRepository dispositionRepository;
    private final DispositionValidation dispositionValidation;

    public List<DispositionDto> getDispositionByEmployeeCode(String employeeCode) {
        return dispositionRepository.getAllByEmployeeCode(employeeCode).stream()
                .map(Disposition::toDto)
                .toList();
    }

    public List<DispositionDto> getAllDisposition() {
        return dispositionRepository.getAll().stream()
                .map(Disposition::toDto)
                .toList();
    }

    public DispositionDto updateDisposition(DispositionDto dispositionDto) {
        DispositionDto checkedDisposition = dispositionValidation.validateDisposition(dispositionDto);
        return dispositionRepository.updateDisposition(checkedDisposition)
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