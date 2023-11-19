package uz.edm;

import uz.edm.model.Role;
import uz.edm.model.dto.EmployeeDto;
import uz.edm.service.DispositionService;
import uz.edm.service.EmployeeService;
import uz.edm.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EDMDispositionLoader implements InitializingBean {

    private final DispositionService dispositionService;
    private final TimeEntryService timeEntryService;
    private final EmployeeService employeeService;

    @Override
    public void afterPropertiesSet() {

        employeeService.createEmployee(new EmployeeDto(
                "EDM-API",
                "EDM-API",
                "EDM",
                "EDM-API-1",
                "EDM-API@EDM.COM",
                LocalDate.now(),
                Role.INTERNAL,
                "EDM-API111",
                "EDM-001"));

        employeeService.createEmployee(new EmployeeDto(
                "EDM-API",
                "EDM-API",
                "EDM",
                "EDM-API-1",
                "user@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "EDM-002"));




//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-001"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "16:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "21:00", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "11:30", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "20:00", "22:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-004"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-005"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "12:00", "NF-006"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "16:00", "22:00", "NF-006"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "11:30", "NF-008"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:30", "22:00", "NF-008"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "09:00", "14:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "11:30", "NF-011"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "20:00", "22:00", "NF-011"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "09:00", "15:00", "NF-012"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "20:00", "22:00", "NF-012"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-013"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-014"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "09:00", "17:00", "NF-015"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "20:00", "NF-016"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "18:00", "NF-017"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "22:00", "NF-018"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "10:00", "14:00", "NF-019"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-019"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "14:00", "NF-020"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "22:00", "NF-021"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "14:00", "NF-022"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-022"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "NF-023"));

//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "15:30", "22:00", "HONIA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "18:00", "22:00", "DENIS"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "15:00", "WERA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "16:00", "NATKA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "14:00", "KUBA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "14:00", "22:00", "KUBA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "15:00", "ZENIA"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "15:00", "ANGELA"));


//        timeEntryService.createTimeEntry(new TimeEntryDto(LocalDate.now().minusDays(1), "12:00", "18:00", "NF-002"));

//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "16:00", "NF-001"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "08:00", "18:00", "NF-008"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "09:00", "13:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "09:00", "12:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "10:00", "22:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "10:00", "16:00", "NF-004"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "12:00", "18:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "14:00", "18:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "16:00", "22:00", "NF-005"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "17:00", "22:00", "NF-006"));


    }
}
