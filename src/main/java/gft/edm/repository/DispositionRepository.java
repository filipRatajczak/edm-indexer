package gft.edm.repository;

import gft.edm.model.Disposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispositionRepository extends JpaRepository<Disposition, UUID> {
}
