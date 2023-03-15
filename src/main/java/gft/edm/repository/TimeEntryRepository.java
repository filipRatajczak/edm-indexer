package gft.edm.repository;

import gft.edm.model.TimeEntry;
import gft.edm.model.dto.TimeEntryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {

    @Query(value = """
                    select te
                    from TimeEntry te
                    where te.employeeCode = ?1
            """)
    List<TimeEntry> getAllByEmployeeCode(String employeeCode);

    @Query(value = """
                    from TimeEntry te
            """)
    List<TimeEntry> getAll();

    @Modifying
    @Query(value = """
                    update TimeEntry te
                    set te.day = :#{#timeEntryDto.day},
                        te.start = :#{#timeEntryDto.start},
                        te.stop = :#{#timeEntryDto.stop}
                    where te.employeeCode = :#{#timeEntryDto.employeeCode}
            """)
    Optional<TimeEntry> updateDisposition(@Param("timeEntryDto") TimeEntryDto timeEntryDto);

    @Modifying
    void deleteById(UUID id);

}
