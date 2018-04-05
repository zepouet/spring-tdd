package fr.treeptik.training.player;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import fr.treeptik.training.PlayerNotFoundException;
import fr.treeptik.training.grille.Player;
import fr.treeptik.training.grille.PlayerRepository;
import fr.treeptik.training.grille.TypePlayer;
import fr.treeptik.training.service.PlayerService;
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
public class PlayerServiceTest {

    @TestConfiguration
    static class PlayerServiceTestContextConfiguration {
        @Bean
        public PlayerService playerService() {
            return new PlayerService();
        }
    }

    @Autowired
    private PlayerService playerService;

    @MockBean
    private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        Player nicolas = new Player("n.muller@treeptik.fr", "Nicolas Muller", TypePlayer.CASUAL);
        Player william = new Player("w.bartlett@treeptik.fr", "William Bartlett", TypePlayer.REGULAR);

        List<Player> allPlayers = Arrays.asList(nicolas, william);

        Mockito.when(playerRepository.findById(nicolas.getEmail())).thenReturn(Optional.of(nicolas));
        Mockito.when(playerRepository.findById("wrong_name")).thenReturn(null);
        Mockito.when(playerRepository.findAll()).thenReturn(allPlayers);
        Mockito.when(playerRepository.findById("undef")).thenReturn(null);
    }

    @Test
    public void rechercheJoueurParEmail() {
        Player nicolas = playerService.getDetails("n.muller@treeptik.fr");
        assertThat(nicolas.getName()).isEqualTo("Nicolas Muller");
        assertThat(nicolas.getType()).isEqualTo(TypePlayer.CASUAL);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void rechercherJoueurInexistant() throws Exception {
        playerService.getDetails("nausicaa.muller@treeptik.fr");
    }

}
