package fr.treeptik.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.treeptik.CoinsApplication;
import fr.treeptik.domain.CoinChange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CoinChangeController.class)

public class CoinChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void siMontantCorrectAlorsRendreMonnaie() throws Exception {
        this.mockMvc.perform(get("/gain/{montant}", 203))
                .andExpect(jsonPath("pieces").isArray())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"pieces\":[100,100,2,1]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void siMontantInCorrectAlorsRetournerErreur() throws Exception {
        this.mockMvc.perform(get("/gain/{montant}", -1))
                .andExpect(status().is4xxClientError());
    }
}
