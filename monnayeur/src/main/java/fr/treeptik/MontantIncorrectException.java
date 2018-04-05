package fr.treeptik;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Montant is not valid.")
public class MontantIncorrectException extends RuntimeException {
}
