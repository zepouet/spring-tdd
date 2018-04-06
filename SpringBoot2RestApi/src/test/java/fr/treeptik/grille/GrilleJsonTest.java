package fr.treeptik.training.grille;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;

public class GrilleJsonTest {

    private JacksonTester<Grille> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testGrilleGagnante() throws IOException {
        Grille grille = new Grille(1, 2, 3);
        grille.setId(UUID.fromString("b830d563-2d74-47e6-b8c3-edc15b8f2392"));
        grille.setGagnante(true);
        grille.setGain(4920);
        assertThat(this.json.write(grille))
                .isEqualToJson("grille-gagnante.json");
    }

    @Test
    public void testGrilleDetailleeGagnante() throws IOException {
        Grille grille = new Grille(1, 2, 3);
        grille.setId(UUID.fromString("b830d563-2d74-47e6-b8c3-edc15b8f2392"));
        grille.setGain(23);
        grille.setPieces(Arrays.asList(4,4,15));
        System.out.println(json.write(grille));
        assertThat(this.json.write(grille))
                .isEqualToJson("grille-gagnante-detaillee.json");
    }

    @Test
    public void testGrillePerdante() throws IOException {
        Grille grille = new Grille(1, 2, 4);
        grille.setId(UUID.fromString("b830d563-2d74-47e6-b8c3-edc15b8f2392"));
        System.out.println(json.write(grille));
        assertThat(this.json.write(grille))
                .isEqualToJson("grille-perdante.json");
    }

}
