package gft.edm.repository;

import gft.edm.model.Disposition;
import gft.edm.model.dto.DispositionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DispositionRepository extends JpaRepository<Disposition, UUID> {

    @Query(value = """
                    select d
                    from Disposition d
                    where d.employeeCode = ?1
            """)
    List<Disposition> getAllByEmployeeCode(String employeeCode);

    @Query(value = """
                    select d
                    from Disposition d
            """)
    List<Disposition> getAll();

    @Modifying
    @Query(value = """
                    update Disposition d
                    set d.day = :#{#dispositionDto.day},
                        d.start = :#{#dispositionDto.start},
                        d.stop = :#{#dispositionDto.stop}
                    where d.employeeCode = :#{#dispositionDto.employeeCode}
            """)
    Optional<Disposition> updateDisposition(@Param("dispositionDto") DispositionDto dispositionDto);

    @Modifying
    void deleteById(UUID id);

}
