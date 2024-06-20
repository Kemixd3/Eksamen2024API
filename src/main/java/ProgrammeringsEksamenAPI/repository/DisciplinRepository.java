package ProgrammeringsEksamenAPI.repository;

import ProgrammeringsEksamenAPI.models.Disciplin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinRepository extends JpaRepository<Disciplin, Long> {

    Disciplin findByNavn(String navn);
    List<Disciplin> findAll();
}
