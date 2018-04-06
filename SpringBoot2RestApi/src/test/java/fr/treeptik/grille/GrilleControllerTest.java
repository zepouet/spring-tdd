package fr.treeptik.training.grille;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import fr.treeptik.training.coin.Monnaie;
import fr.treeptik.training.controller.GrilleController;
import fr.treeptik.training.controller.PlayersController;
import fr.treeptik.training.service.CoinService;
import fr.treeptik.training.service.GrilleService;
import fr.treeptik.training.service.PlayerService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GrilleController.class)
public class GrillesControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GrilleService grilleService;

	@Test
	public void rechercherGrille() throws Exception {
		Grille grille = new Grille(1,2,3);
		UUID uuid = UUID.randomUUID();
		grille.setId(uuid);

		when(grilleService.findById(any())).thenReturn(grille);
		ResultActions actions = this.mockMvc.perform(get("/grille/{uuid}", uuid.toString()))
				.andExpect(jsonPath("id").value(uuid.toString()))
				.andExpect(jsonPath("gagnante").value(true))
				.andExpect(jsonPath("gain").value(grille.getGain()))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
