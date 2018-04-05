package fr.treeptik.training.player;

import static org.assertj.core.api.Assertions.*;

import fr.treeptik.training.grille.Player;
import fr.treeptik.training.grille.PlayerRepository;
import fr.treeptik.training.grille.TypePlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepositoryTests {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void rechercherJoueurParEmail() throws Exception {
		Player nicolas = new Player("n.muller@treeptik.fr", "Nicolas Muller", TypePlayer.CASUAL);
		Player saved = testEntityManager.persistFlushFind(nicolas);
		Player player = playerRepository.findById("n.muller@treeptik.fr").get();
		assertThat(player.getName()).isEqualTo(saved.getName());
		assertThat(player.getType()).isEqualTo(saved.getType());
	}
}