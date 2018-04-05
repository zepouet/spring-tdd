package fr.treeptik.training.coin;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import fr.treeptik.training.service.CoinService;
import fr.treeptik.training.service.GrilleService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CoinServiceTest {

    @TestConfiguration
    static class CoinServiceTestContextConfiguration {
        @Bean
        public CoinService coinService() {
            return new CoinService();
        }
    }

    @Autowired
    private CoinService coinService;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9999);

    @Test
    public void retournePiecesPourMontant() throws IOException {

        stubFor(get(urlPathMatching("/gain/montant/203"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"pieces\":[100,100,2,1]}")));

        try {
            Monnaie pieces = coinService.pieces(203);
            assertThat(pieces.pieces, Matchers.containsInAnyOrder(100, 2, 100, 1));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }


    }



}
