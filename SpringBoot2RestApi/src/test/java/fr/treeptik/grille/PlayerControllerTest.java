package fr.treeptik.training.player;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.treeptik.training.PlayerNotFoundException;
import fr.treeptik.training.controller.PlayersController;
import fr.treeptik.training.grille.Grille;
import fr.treeptik.training.grille.Player;
import fr.treeptik.training.grille.PlayerRepository;
import fr.treeptik.training.grille.TypePlayer;
import fr.treeptik.training.service.GrilleService;
import fr.treeptik.training.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlayersController.class)
public class PlayersControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private PlayerRepository playerRepository;

	@MockBean
	private GrilleService grilleService;

	@Test
	public void recupererDetailJoueur() throws Exception {
		when(this.playerService.getDetails("n.muller@treeptik.fr"))
				.thenReturn(new Player("n.muller@treeptik.fr","Nicolas Muller", TypePlayer.CASUAL));

		this.mockMvc.perform(get("/players/{email}", "n.muller@treeptik.fr"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("Nicolas Muller"))
				.andExpect(jsonPath("type").value(TypePlayer.CASUAL.toString()));
	}

	@Test
	public void rechercherJoueurInexistant() throws Exception {
		when(this.playerService.getDetails(any())).thenThrow(new PlayerNotFoundException());
		this.mockMvc.perform(get("/players/{email}", "nausicaa.muller@gmail.com"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void acheterUneGrille() throws Exception {
		Player casual = new Player("n.muller@treeptik.fr","Nicolas Muller", TypePlayer.CASUAL);
		when(playerService.getDetails("n.muller@treeptik.fr")).thenReturn(casual);
		when(playerService.incrementCompteur(any())).thenReturn(casual);
		when(playerRepository.saveAndFlush(any(Player.class))).thenReturn(casual);

		Grille grille = new Grille(1,2,3);
		grille.setId(UUID.randomUUID());
		when(grilleService.findById(any())).thenReturn(grille);

		ResultActions resultActions = this.mockMvc.perform(
							post("/players/{email}/grille", "n.muller@treeptik.fr")
							.contentType(MediaType.APPLICATION_JSON)
							.content(asJsonString(grille))
				).andExpect(status().isCreated())
				.andExpect(header().string("location",
						containsString("/grille/"+grille.getId())))
				.andDo(print());
	}


	@Test
	public void nePlusPouvoirAcheterDeGrille() throws Exception {
		Player casual = new Player("n.muller@treeptik.fr","Nicolas Muller", TypePlayer.CASUAL);
		when(playerService.getDetails("n.muller@treeptik.fr")).thenReturn(casual);
		Player casual3 = new Player("n.muller@treeptik.fr","Nicolas Muller", TypePlayer.CASUAL);
		casual3.incrementCompteur();
		casual3.incrementCompteur();
		casual3.incrementCompteur();
		when(playerService.incrementCompteur(any())).thenReturn(casual3);
		when(playerRepository.saveAndFlush(any(Player.class))).thenReturn(casual3);

		Grille grille = new Grille(1,2,3);
		grille.setId(UUID.randomUUID());
		when(grilleService.findById(any())).thenReturn(grille);

		ResultActions resultActions = this.mockMvc.perform(
				post("/players/{email}/grille", "n.muller@treeptik.fr")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(grille))
		).andExpect(status().isForbidden());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
