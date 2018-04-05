package fr.treeptik.training.grille;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GrilleRepositoryTests {

	@Autowired
	private GrilleRepository grilleRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void persisterGrilleGagnante() throws Exception {
		Grille gagnante = new Grille(1, 2, 3);
		Grille saved = testEntityManager.persistFlushFind(gagnante);
		Grille grille = grilleRepository.findById(saved.getId()).get();
		assertThat(grille.getNumeros()).isEqualTo(saved.getNumeros());
	}


}