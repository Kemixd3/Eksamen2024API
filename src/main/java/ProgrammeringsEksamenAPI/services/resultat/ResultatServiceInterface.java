package ProgrammeringsEksamenAPI.services.resultat;

import dto.resultat.ResultatRequest;
import org.springframework.http.ResponseEntity;

public interface ResultatServiceInterface {
    ResponseEntity<?> addPointsResultat(Long disciplinId, Long deltagerId, ResultatRequest request);
    ResponseEntity<?> addTimeResultat(Long disciplinId, Long deltagerId, ResultatRequest request);
    ResponseEntity<?> addDistanceResultat(Long disciplinId, Long deltagerId, ResultatRequest request);
}
