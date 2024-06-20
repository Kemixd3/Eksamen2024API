package ProgrammeringsEksamenAPI.repository;


import ProgrammeringsEksamenAPI.models.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
}
