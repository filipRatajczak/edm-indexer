package uz.edm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.edm.exception.EmployeeNotFoundException;
import uz.edm.model.Organization;
import uz.edm.repository.OrganizationRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;


    public Set<Organization> getAllOrganizationsByOrganizationCodes(Set<String> organizationCodes) {

        return organizationRepository.findAllOrganizationsByOrganizationCodes(organizationCodes);

    }

    public Set<String> mapOrganizationsToOrganizationCodes(Set<Organization> organizations) {

        return organizations.stream().map(Organization::getOrganizationCode).collect(Collectors.toSet());

    }

    public Organization getOrganizationByOrganizationCode(String organizationCode) {
        return organizationRepository.findOrganizationByEmployeeCode(organizationCode)
                .orElseThrow(() -> new EmployeeNotFoundException("Organization with organizationCode: [" + organizationCode + "] does not found."));
    }


}
