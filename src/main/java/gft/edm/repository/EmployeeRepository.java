package gft.edm.repository;

import gft.edm.model.Disposition;
import gft.edm.model.Employee;
import gft.edm.model.dto.DispositionDto;
import gft.edm.model.dto.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = """
                    select e
                    from Employee e
            """)
    List<Employee> getAll();

    @Query(value = """
            select e
            from Employee e
            where e.employeeCode = ?1
    """)
    Optional<Employee> getByEmployeeCode(String employeeCode);

    @Modifying
    @Query(value = """
                    update Employee e
                    set e.firstName = :#{#employeeDto.firstName},
                        e.lastName = :#{#employeeDto.lastName},
                        e.birthday = :#{#employeeDto.birthday},
                        e.address = :#{#employeeDto.address},
                        e.phoneNumber = :#{#employeeDto.phoneNumber},
                        e.email = :#{#employeeDto.email}
                    where e.employeeCode = :employeeCode
            """)
    Optional<Employee> updateEmployee(@Param("employeeCode") String employeeCode, @Param("employeeDto") EmployeeDto employeeDto);

    @Modifying
    void deleteById(UUID id);
}
