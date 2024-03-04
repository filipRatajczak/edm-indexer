package uz.edm.repository;

import uz.edm.model.TimeEntry;
import uz.edm.model.dto.TimeEntryDto;
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
public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {

    @Query(value = """
                    select te
                    from TimeEntry te
                    where te.employeeCode = ?1
                    and te.day between ?2 and ?3
            """)
    List<TimeEntry> getAllByEmployeeCode(String employeeCode, LocalDate start, LocalDate stop);

    @Query(value = """
                    from TimeEntry te
            """)
    List<TimeEntry> getAll();


    @Query(value = """
                    select te
                    from TimeEntry te
                    where te.employeeCode = ?1
                    and te.day between ?2 and ?3
            """)
    List<TimeEntry> getAllByEmployeeCodeInTimePeriod(String employeeCode, LocalDate start, LocalDate stop);

    @Modifying
    @Query(value = """
                    update TimeEntry te
                    set te.day = :#{#timeEntryDto.day},
                        te.start = :#{#timeEntryDto.start},
                        te.stop = :#{#timeEntryDto.stop}
                    where te.employeeCode = :employeeCode
            """)
    Optional<TimeEntry> updateDisposition(@Param("employeeCode") String employeeCode, @Param("timeEntryDto") TimeEntryDto timeEntryDto);

    @Modifying
    void deleteById(UUID id);

}
