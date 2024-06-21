package ProgrammeringsEksamenAPI;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import ProgrammeringsEksamenAPI.repository.ResultatRepository;
import ProgrammeringsEksamenAPI.services.resultat.ResultatService;
import ProgrammeringsEksamenAPI.services.resultat.ResultatServiceInterface;
import dto.resultat.ResultatRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ResultatIntegrationTest {

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ResultatServiceInterface resultatService2;
    // Det var lige de integrations tests jeg kunne nå og som føler giver mest mening. Oprette deltager, discipliner og resultater
    @Test
    public void testCreateDisciplinDeltagerAndResultat() {
        // Opret Disciplin
        Disciplin disciplin = new Disciplin();
        disciplin.setNavn("Swimming");
        disciplinRepository.save(disciplin);

        // Opret Deltager
        Deltager deltager = new Deltager();
        deltager.setNavn("John Doe");
        deltagerRepository.save(deltager);

        // Initial resultater
        long initialResultaterCount = resultatRepository.count();

        // Create point ResultatRequest with specific date and points
        ResultatRequest request = new ResultatRequest();
        request.setDato(LocalDate.of(2024, 6, 21)); // Set a specific date
        request.setPoints(100);
        request.setResultatType("point");

        ResponseEntity<?> response = resultatService2.addPointsResultat(disciplin.getId(), deltager.getId(), request);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        // Verify that Resultat is created and matches the test asserts
        List<Resultat> resultater = resultatRepository.findAll();
        assertThat(resultater).hasSize((int) (initialResultaterCount + 1));

        // The index of the just created Resultat in the list should we see also in `initialResultaterCount` :)
        Resultat createdResultat = resultater.get((int) initialResultaterCount);
        assertThat(createdResultat.getDisciplin()).isEqualTo(disciplin);
        assertThat(createdResultat.getDeltager()).isEqualTo(deltager);
        assertThat(createdResultat.getDato()).isEqualTo(request.getDato());

        if (createdResultat instanceof PointResultat) {
            assertThat(((PointResultat) createdResultat).getPoints()).isEqualTo(request.getPoints());
        } else if (createdResultat instanceof TimeResultat) {
            // We dont have any of these yet :)
        }
    }
}



