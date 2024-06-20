package ProgrammeringsEksamenAPI.repository;


import ProgrammeringsEksamenAPI.models.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    @Query("SELECT new map(r.distance as distance, r.timeTaken as time_taken, r.deltager.id as deltager_id, r.disciplin.id as disciplin_id, r.id as id) " +
            "FROM Resultat r WHERE r.deltager.id = :deltagerId")
    List<Map<String, Object>> findResultaterByDeltagerId(@Param("deltagerId") Long deltagerId);




    @Modifying
    @Query("DELETE FROM Resultat r WHERE r.id = :resultatId AND r.deltager.id = :deltagerId")
    void deleteByIdAndDeltagerId(@Param("resultatId") Long resultatId, @Param("deltagerId") Long deltagerId);
    @Query("SELECT new map(r.distance as distance, r.deltager.id as deltager_id, r.disciplin.id as disciplin_id, r.id as id, r.timeTaken as time_taken, r.points as points, d.navn as disciplin_name, d.resultattype as resultattype) " +
            "FROM Resultat r " +
            "LEFT JOIN r.disciplin d " +
            "WHERE r.deltager.id = :deltagerId")
    List<Map<String, Object>> findResultaterWithDisciplinByDeltagerId(Long deltagerId);

}
