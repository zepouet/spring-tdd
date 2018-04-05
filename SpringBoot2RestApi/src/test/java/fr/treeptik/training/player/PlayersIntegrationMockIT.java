package fr.treeptik.training.player;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import fr.treeptik.training.grille.Player;
import fr.treeptik.training.grille.PlayerRepository;
import fr.treeptik.training.grille.TypePlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayersIntegrationMockIT {

    @LocalServerPort
    private int port;

    @MockBean
    public PlayerRepository playerRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getPlayer() {
        Player nicolas = new Player("n.muller@treeptik.fr", "Nicolas Muller", TypePlayer.CASUAL);
        Mockito.when(playerRepository.findById(nicolas.getEmail())).thenReturn(Optional.of(nicolas));

        ResponseEntity<Player> responseEntity =
                testRestTemplate.getForEntity("http://localhost:"+port+"/players/{email}"
                        , Player.class, "n.muller@treeptik.fr");
        Player player = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(player.getName()).isEqualTo("Nicolas Muller");
        assertThat(player.getType()).isEqualByComparingTo(TypePlayer.CASUAL);
    }

}
