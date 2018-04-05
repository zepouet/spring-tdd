package fr.treeptik.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class Monnaie {

    public List<Integer> pieces;

    public Monnaie() {
    }

    public Monnaie(List<Integer> pieces) {
        this.pieces = pieces;
    }

    public List<Integer> getPieces() {
        return pieces;
    }

}
