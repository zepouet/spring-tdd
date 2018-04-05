package fr.treeptik.domain;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import java.util.List;

import fr.treeptik.domain.CoinChange;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CoinChangeTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testAvecUneSeulePieceARendre() {
        CoinChange coinChange = new CoinChange(asList(25));
        assertEquals(
                singletonList(25),
                coinChange.rendre(25));
    }

    //@Ignore
    @Test
    public void foo() {
        CoinChange coinChange = new CoinChange(asList(100, 25, 10, 5));
        assertEquals(
                singletonList(25),
                coinChange.rendre(25));
    }


    //@Ignore
    @Test
    public void testAvecUneSeulePieceARendreMaisPlusieursPiecesDisponibles() {
        CoinChange coinChange = new CoinChange(asList(100, 25, 10, 5, 2, 1));
        assertEquals(
                singletonList(25),
                coinChange.rendre(25));
    }

    @Test
    public void testAvecUneSeulePieceARendreMaisPlusieursPiecesDisponiblesRangesAleatoirement() {
        CoinChange coinChange = new CoinChange(asList(1, 4, 15, 20, 50, 100, 200, 500));
        List<Integer> combinaison = coinChange.rendre(23);
        assertThat(combinaison, Matchers.containsInAnyOrder(4, 15, 4));
    }

    @Test
    public void testAvecUneSeulePieceARendreMaisTroisPieces3() {
        CoinChange coinChange = new CoinChange(asList(1, 7, 23));
        List<Integer> combinaison = coinChange.rendre(28);
        assertThat(combinaison, Matchers.containsInAnyOrder(7, 7, 7, 7));
    }

    //@Ignore
    @Test
    public void testAvecUnGrandNombredePieces() {
        // Attention les pièces ont changé :)
        CoinChange coinChange = new CoinChange(asList(1, 2, 5, 10, 20, 50, 100));
        List<Integer> combinaison = coinChange.rendre(999);
        assertThat(combinaison, Matchers.containsInAnyOrder(100, 100, 100, 100, 100, 100, 100, 100, 100, 50, 20, 20, 5, 2, 2));
    }

    @Test
    public void testLeCompteEstBonAvecDeuxPiecesSeulement() {
        CoinChange coinChange = new CoinChange(asList(4, 5));

        assertEquals(
                asList(5, 5, 5, 4, 4, 4),
                coinChange.rendre(27));
    }

    @Test
    public void testLeCompteEstBonSansPieceUnitaire() {
        CoinChange coinChange = new CoinChange(asList(2, 5, 10, 20, 50));
        assertEquals(
                asList(10, 5, 2, 2, 2),
                coinChange.rendre(21));
    }

    //@Ignore
    @Test
    public void testLeCLientQuiNaPasDeBillet() {
        CoinChange coinChange = new CoinChange(asList(1, 5, 10, 21, 25));

        assertEquals(emptyList(),
                coinChange.rendre(0));
    }

    @Test
    public void testImpossibleDeRendreLaMonnaieCarMontantTropPetit() {
        CoinChange coinChange = new CoinChange(asList(5, 10));

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Un montant de 3 est trop petit.");

        assertEquals(emptyList(),
                coinChange.rendre(3));
    }


    @Test
    public void testImpossibleDeRendreLaMonnaieToutSimplement() {
        CoinChange coinChange = new CoinChange(asList(5, 10));

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Le montant du chéque de 94 dollars" +
                " ne peut être rendu avec ces pièces.");

        assertEquals(emptyList(),
                coinChange.rendre(94));
    }

    @Test
    public void testMontantNegatif() {
        CoinChange coinChange = new CoinChange(asList(1, 2, 5));

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("On ne rend pas la monnaie aux gens qui font crédit");

        assertEquals(emptyList(),
                coinChange.rendre(-5));
    }

}