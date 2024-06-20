package ProgrammeringsEksamenAPI.repository;


import ProgrammeringsEksamenAPI.models.Deltager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeltagerRepository extends JpaRepository<Deltager, Long> {

    List<Deltager> findByNavnContainingIgnoreCase(String navn);


    @Query("SELECT DISTINCT d FROM Deltager d LEFT JOIN FETCH d.discipliner")
    List<Deltager> findAllWithDiscipliner();
    @Query(value = "SELECT DISTINCT d.* " +
            "FROM deltagere d " +
            "LEFT JOIN deltager_disciplin dd ON d.id = dd.deltager_id " +
            "LEFT JOIN discipliner di ON dd.disciplin_id = di.id " +
            "WHERE (:køn IS NULL OR LOWER(d.køn) = LOWER(:køn)) " +
            "AND (:minAlder IS NULL OR d.alder >= :minAlder) " +
            "AND (:maxAlder IS NULL OR d.alder <= :maxAlder) " +
            "AND (:klub IS NULL OR LOWER(d.klub) = LOWER(:klub)) " +
            "AND (:disciplin IS NULL OR LOWER(di.navn) = LOWER(:disciplin)) " +
            "AND (:navn IS NULL OR LOWER(d.navn) LIKE LOWER(CONCAT('%', :navn, '%'))) " +
            "LIMIT 10",
            nativeQuery = true)
    List<Deltager> filterDeltagere(@Param("køn") String køn,
                                   @Param("minAlder") Integer minAlder,
                                   @Param("maxAlder") Integer maxAlder,
                                   @Param("klub") String klub,
                                   @Param("disciplin") String disciplin,
                                   @Param("navn") String navn);


}
