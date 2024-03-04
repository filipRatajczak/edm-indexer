package uz.edm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.edm.model.Organization;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    @Query(value = """
            select o from Organization o
            where o.organizationCode in :orgCodes
            """)
    Set<Organization> findAllOrganizationsByOrganizationCodes(@Param("orgCodes") Set<String> organizationCodes);

    @Query(value = """
                    select o from Organization o
                    where o.organizationCode = :orgCode
            """)
    Optional<Organization> findOrganizationByEmployeeCode(@Param("orgCode") String organizationCode);

}
