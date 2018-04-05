package fr.treeptik.training.grille;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import fr.treeptik.training.GrilleNotFoundException;
import fr.treeptik.training.service.GrilleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GrilleServiceImplTest {

    @TestConfiguration
    static class GrilleServiceImplTestContextConfiguration {
        @Bean
        public GrilleService grilleService() {
            return new GrilleService();
        }
    }

    @Autowired
    private GrilleService grilleService;

    @MockBean
    private GrilleRepository grilleRepository;

    private UUID uuid1;
    private UUID uuid2;
    private Grille grille1;

    @Before
    public void setUp() {
        grille1 = new Grille(1,2,3);

        uuid1 = UUID.randomUUID();
        uuid2 = UUID.randomUUID();
        Mockito.when(grilleRepository.findById(uuid1)).thenReturn(Optional.of(grille1));
        Mockito.when(grilleRepository.findById(uuid2)).thenReturn(Optional.empty());
    }

    @Test
    public void rechercheGrilleGagnante() {
        Grille grille = grilleService.findById(uuid1);
        assertThat(grille.getNumeros()).isEqualTo(grille1.getNumeros());
    }

    @Test(expected = GrilleNotFoundException.class)
    public void rechercherGrilleInexistante() throws Exception {
        grilleService.findById(uuid2);
    }

}
