package fr.treeptik.controller;

import java.util.List;

import fr.treeptik.MontantIncorrectException;
import fr.treeptik.domain.CoinChange;
import fr.treeptik.domain.Monnaie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/gain")
@RestController
public class CoinChangeController {

    @Autowired
    public CoinChange coinChange;

    @GetMapping("/{montant}")
    public ResponseEntity<Monnaie> recupererGain(@PathVariable Integer montant) {
        try {
            List<Integer> pieces = coinChange.rendre(montant);
            Monnaie monnaie = new Monnaie(pieces);
            return ResponseEntity.ok(monnaie);
        } catch (IllegalArgumentException ex) {
            throw new MontantIncorrectException();
        }
    }

}
