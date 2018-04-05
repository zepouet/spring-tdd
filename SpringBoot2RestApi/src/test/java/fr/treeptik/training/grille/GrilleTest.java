package fr.treeptik.training.grille;

import static org.junit.Assert.*;

import org.junit.Test;

public class GrilleTest {

    @Test
    public void test_GrillesDifferentes() {
        Grille grille1 = new Grille(1, 2, 3);
        Grille grille2 = new Grille(1, 2, 4);
        assertNotEquals(grille1, grille2);
    }

    @Test
    public void test_GrilleEgale() {
        Grille grille1 = new Grille(1, 2, 3);
        Grille grille2 = new Grille(3, 2, 1);
        assertEquals(grille1, grille2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nonValideCarNumeroNul() {
        new Grille(0, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nonValideCarNegatif() {
        new Grille(-1, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nonValideCarDoublon() {
        new Grille(2, 1, 1);
    }

    @Test
    public void winning(){
        Grille grille1 = new Grille(1, 2, 3);
        assertEquals("Grille gagnante", Boolean.TRUE, grille1.winning());
    }

    @Test
    public void losing(){
        Grille grille1 = new Grille(1, 2, 4);
        assertEquals("Grille gagnante", Boolean.FALSE, grille1.winning());
    }
}