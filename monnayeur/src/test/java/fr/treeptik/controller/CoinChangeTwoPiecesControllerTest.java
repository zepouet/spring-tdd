package fr.treeptik.controller;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.treeptik.domain.CoinChange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CoinChangeController.class)

public class CoinChangeTwoPiecesControllerTest {

    @TestConfiguration
    static class CoinChangeServiceImplTestContextConfiguration {
        @Bean
        public CoinChange coinChange() {
            return new CoinChange(asList(4, 5));
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void siMontantCorrectAlorsRendreMonnaie() throws Exception {
        this.mockMvc.perform(get("/gain/{montant}", 27))
                .andExpect(jsonPath("pieces").isArray())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"pieces\":[5, 5, 5, 4, 4, 4]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void siMontantInCorrectAlorsRetournerErreur() throws Exception {
        this.mockMvc.perform(get("/gain/{montant}", -1))
                .andExpect(status().is4xxClientError());
    }
}
