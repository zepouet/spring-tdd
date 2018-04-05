package fr.treeptik.training.grille;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import fr.treeptik.training.controller.GrilleController;
import fr.treeptik.training.controller.PlayersController;
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
	public void rechercherGrilleGagnante() throws Exception {
		Grille grille = new Grille(1,2,3);
		UUID uuid = UUID.randomUUID();
		grille.setId(uuid);
		when(grilleService.findById(any())).thenReturn(grille);
		ResultActions actions = this.mockMvc.perform(get("/grille/{uuid}/gain", uuid.toString()))
				.andExpect(jsonPath("id").value(uuid.toString()))
				.andExpect(status().isOk());
		String contentAsString  = actions.andReturn().getResponse().getContentAsString();
		System.out.println(contentAsString);
		Grille grille1 = (new ObjectMapper()).readValue(contentAsString, Grille.class);
		Assert.assertTrue(grille1.winning());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}