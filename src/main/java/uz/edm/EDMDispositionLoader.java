package uz.edm;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import uz.edm.model.Employee;
import uz.edm.model.Organization;
import uz.edm.model.Role;
import uz.edm.model.dto.DispositionDto;
import uz.edm.model.dto.EmployeeDto;
import uz.edm.repository.EmployeeRepository;
import uz.edm.repository.OrganizationRepository;
import uz.edm.service.DispositionService;
import uz.edm.service.EmployeeService;

import java.time.LocalDate;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class EDMDispositionLoader implements InitializingBean {

    private final EmployeeRepository employeeRepository;
    private final DispositionService dispositionService;
    private final EmployeeService employeeService;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
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
                "EDM-001",
                new HashSet<>()));


        Employee employeeByEmployeeCode = employeeRepository.getByEmployeeCode("EDM-001").get();
        organizationRepository.save(new Organization("NorthFishOrganization", "ORG-NF", new HashSet<>(), employeeByEmployeeCode, new HashSet<>()));
//        employeeByEmployeeCode = employeeRepository.getByEmployeeCode("EDM-001").get();
//        Organization organizationByEmployeeCode = organizationRepository.findOrganizationByEmployeeCode("ORG-NF").get();
//        organizationByEmployeeCode.getEmployees().add(employeeByEmployeeCode);
//        employeeByEmployeeCode.getOrganizations().add(organizationByEmployeeCode);
//        organizationRepository.save(organizationByEmployeeCode);
//        employeeRepository.save(employeeByEmployeeCode);


        employeeService.createEmployee(new EmployeeDto(
                "EDM-CORE",
                "EDM-CORE",
                "EDM",
                "EDMCORE-1",
                "EDM-CORE@EDM.COM",
                LocalDate.now(),
                Role.INTERNAL,
                "EDM-CORE111",
                "EDM-002",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Weronika",
                "Podpieniak",
                "EDM",
                "EDM-API-1",
                "NF-001@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-001",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Denis",
                "Kowalski",
                "EDM",
                "EDM-API-1",
                "NF-002@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-002",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Maja",
                "Lis",
                "EDM",
                "EDM-API-1",
                "NF-003@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-003",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Patrycja",
                "Morszczuk",
                "EDM",
                "EDM-API-1",
                "NF-004@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-004",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Piotr",
                "Głuś",
                "EDM",
                "EDM-API-1",
                "NF-005@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-005",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Honorata",
                "Majewska",
                "EDM",
                "EDM-API-1",
                "NF-006@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-006",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Natalia",
                "Jeleń",
                "EDM",
                "EDM-API-1",
                "NF-007@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-007",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Natka",
                "Maciejewska",
                "EDM",
                "EDM-API-1",
                "NF-008@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-008",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Żenia",
                "Kowal",
                "EDM",
                "EDM-API-1",
                "NF-009@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-009",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Jakub",
                "Niemycki",
                "EDM",
                "EDM-API-1",
                "NF-010@nf.com",
                LocalDate.now(),
                Role.EMPLOYEE,
                "haslo111",
                "NF-010",
                new HashSet<>()));

        employeeService.createEmployee(new EmployeeDto(
                "Angelika",
                "Pietrzak",
                "EDM",
                "EDM-API-1",
                "NF-011@nf.com",
                LocalDate.now(),
                Role.MANAGER,
                "haslo111",
                "NF-011",
                new HashSet<>()));

        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "13:00", "20:00", "NF-001", "ORG-NF"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "10:00", "18:00", "NF-001"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "10:00", "18:00", "NF-001"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(0), "18:00", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "16:30", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "18:30", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "16:00", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "16:00", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "14:00", "22:00", "NF-002"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(6), "11:00", "18:00", "NF-002"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(0), "08:00", "16:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "08:00", "16:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "08:00", "16:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "16:00", "22:00", "NF-003"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "12:00", "20:00", "NF-003"));
//
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "09:00", "22:00", "NF-004"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "09:00", "22:00", "NF-004"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(6), "11:00", "18:00", "NF-004"));
//
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(0), "08:00", "13:30", "NF-005"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "08:00", "13:15", "NF-005"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "12:00", "NF-005"));
//
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "15:30", "22:00", "NF-006"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "15:30", "22:00", "NF-006"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(6), "09:00", "19:00", "NF-006"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "13:30", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "16:00", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "08:00", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "16:00", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "09:00", "22:00", "NF-007"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(6), "09:00", "22:00", "NF-007"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "08:00", "16:00", "NF-008"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "16:00", "NF-008"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(0), "08:00", "16:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "08:00", "16:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "08:00", "16:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "08:00", "16:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "13:00", "22:00", "NF-009"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "13:00", "22:00", "NF-009"));
//
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));
//        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(5), "08:00", "18:00", "NF-010"));


    }
}
