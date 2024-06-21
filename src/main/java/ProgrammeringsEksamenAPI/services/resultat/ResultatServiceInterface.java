package ProgrammeringsEksamenAPI.services.resultat;

import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import dto.resultat.ResultatDTO;
import dto.resultat.ResultatRequest;
import dto.resultat.TimeDistanceResultatDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ResultatServiceInterface {
    ResponseEntity<?> addPointsResultat(Long disciplinId, Long deltagerId, ResultatRequest request);
    ResponseEntity<?> addTimeResultat(Long disciplinId, Long deltagerId, ResultatRequest request);
    ResponseEntity<?> addDistanceResultat(Long disciplinId, Long deltagerId, ResultatRequest request);

    //TimeDistanceResultat createTimeDistanceResultat(TimeDistanceResultatDTO dto);
    //ResultatDTO createResultat(ResultatDTO resultatDTO);

    Resultat patchResultat(Long resultatId, Resultat updatedResultat);

    List<Map<String, Object>> getResultaterForDeltager(Long deltagerId);

    //void deleteResultat(Long deltagerId, Long resultatId);

    List<ResponseEntity<?>> registrerResultater(Long disciplinId, List<ResultatRequest> requests);
}
