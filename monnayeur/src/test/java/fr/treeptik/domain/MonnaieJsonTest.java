package fr.treeptik.domain;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;

public class MonnaieJsonTest {

    private JacksonTester<Monnaie> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void serializeJson() throws Exception {

        Monnaie monnaie = new Monnaie(asList(1));
        assertThat(this.json.write(monnaie))
                .isEqualToJson("monnaie-une-piece.json");

        monnaie = new Monnaie(asList(1, 2));
        assertThat(this.json.write(monnaie))
                .isEqualToJson("monnaie-deux-pieces.json");

        monnaie = new Monnaie(asList());
        assertThat(this.json.write(monnaie))
                .isEqualToJson("pas-de-pieces.json");
    }

}
