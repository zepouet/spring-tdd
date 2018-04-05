package fr.treeptik.training.player;

import fr.treeptik.training.grille.Player;
import fr.treeptik.training.grille.TypePlayer;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:data-integration.sql")
public class PlayersIntegrationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getPlayer() {
        ResponseEntity<Player> responseEntity =
                testRestTemplate.getForEntity("http://localhost:"+port+"/players/{email}"
                        , Player.class, "n.muller@treeptik.fr");
        Player player = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(player.getName()).isEqualTo("Nicolas Muller");
        assertThat(player.getType()).isEqualByComparingTo(TypePlayer.CASUAL);
    }

}
