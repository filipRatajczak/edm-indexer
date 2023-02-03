package gft.edm.repository;

import gft.edm.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {
}
