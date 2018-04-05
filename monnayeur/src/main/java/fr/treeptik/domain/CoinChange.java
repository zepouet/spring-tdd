package fr.treeptik.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public final class CoinChange {

    private static final String MONTANT_TROP_PETIT = "Un montant de %d est trop petit.";
    private static final String IMPOSSIBLE = "Le montant du chéque de %d dollars ne peut être rendu avec ces pièces.";
    private static final String MONTANT_NEGATIF = "On ne rend pas la monnaie aux gens qui font crédit";

    private List<Integer> pieces;
    private final ConcurrentMap<Integer, Noeud> rendus = new ConcurrentHashMap<>();

    public CoinChange() {
    }

    public void setPieces(List<Integer> pieces) {
        this.pieces = pieces;
    }

    public CoinChange(List<Integer> pieces) {
        this.pieces = new ArrayList<>(pieces);
        Collections.sort(this.pieces, Collections.reverseOrder());
    }

    public List<Integer> rendre(int montant) {

        if (montant < 0) {
            throw new IllegalArgumentException(MONTANT_NEGATIF);
        }

        if (montant == 0) { return new ArrayList<>(); }

        if (pieces.stream().noneMatch(pieces -> pieces <= montant)) {
            throw new IllegalArgumentException(String.format(MONTANT_TROP_PETIT, montant));
        }

        this.pieces = pieces.stream().filter(p -> p <= montant).collect(Collectors.toList());
        Optional<Noeud> root = Optional.of(new Noeud(montant, 0));
        List<Integer> monnaie =  rendreMonnaie(root).orElseThrow(() -> new IllegalArgumentException(String.format(IMPOSSIBLE, 94)));
        return monnaie;
    }

    public Optional<List<Integer>> rendreMonnaie(Optional<Noeud> root) {
        LinkedList<Optional<Noeud>> fifo = new LinkedList<>();
        fifo.add(root);
        return _rendreMonnaie(fifo);
    }

    public Optional<List<Integer>> _rendreMonnaie(final LinkedList<Optional<Noeud>> fifo) {
        if (fifo.isEmpty()) return Optional.empty();

        System.out.println(fifo);
        LinkedList<Optional<Noeud>> _fifo = new LinkedList<>();
        for (Optional<Noeud> noeud : fifo) {
            for (Integer piece : pieces) {
                Optional<Noeud> noeudPossible = noeud.get().essayerPiece(piece);
                if (noeudPossible.isPresent()) {
                    Noeud unNoeud = noeudPossible.get();
                    if (unNoeud.getCompteur()==0) {
                        return Optional.of(unNoeud.combinaisons);
                    }
                    if (!rendus.containsKey(unNoeud.getCompteur())) {
                        rendus.put(unNoeud.getCompteur(), unNoeud);
                        _fifo.add(noeudPossible);
                    }
                }
            }
        }
        return _rendreMonnaie(_fifo);
    }

    class Noeud {

        private final int compteur;
        private final int piece;
        public final List<Integer> combinaisons;

        public Noeud(int compteur, int piece) {
            this.compteur = compteur - piece;
            this.piece = piece;
            combinaisons = new LinkedList<>();
        }

        public int getCompteur() {
            return compteur;
        }

        public int getPiece() {
            return piece;
        }

        public Optional<Noeud> essayerPiece(final int piece) {
            if (compteur - piece >= 0) {
                //System.out.printf("m:{%d} - p:{%d} ==> %d\n", compteur, piece, compteur - piece);
                final Noeud toAdd = new Noeud(compteur, piece);
                toAdd.combinaisons.addAll(this.combinaisons);
                toAdd.combinaisons.add(piece);
                return Optional.of(toAdd);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public String toString() {
            return "Noeud{" +
                    "compteur=" + compteur +
                    ", piece=" + piece +
                    '}';
        }

    }
}