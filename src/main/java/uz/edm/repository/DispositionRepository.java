package uz.edm.repository;

import uz.edm.model.Disposition;
import uz.edm.model.dto.DispositionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DispositionRepository extends JpaRepository<Disposition, UUID> {

    @Query(value = """
                    select d
                    from Disposition d
                    where d.day between ?1 and ?2
            """)
    List<Disposition> getAll(LocalDate from, LocalDate to);

    @Query(value = """
                    select d
                    from Disposition d
                    where d.employee.employeeCode = ?1
                    and d.day between ?2 and ?3
            """)
    List<Disposition> getAllByEmployeeCodeInTimePeriod(String employeeCode, LocalDate start, LocalDate stop);

    @Modifying
    @Query(value = """
                    update Disposition d
                    set d.day = :#{#dispositionDto.day},
                        d.start = :#{#dispositionDto.start},
                        d.stop = :#{#dispositionDto.stop}
                    where d.employee.employeeCode = :employeeCode
            """)
    Optional<Disposition> updateDisposition(@Param("employeeCode") String employeeCode, @Param("dispositionDto") DispositionDto dispositionDto);

    @Modifying
    void deleteById(UUID id);

}
