package fr.treeptik;

import static java.util.Arrays.asList;

import fr.treeptik.domain.CoinChange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.IfProfileValue;

@SpringBootApplication
public class CoinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinsApplication.class, args);
	}

	// Default CoinChange
	@Bean
    public CoinChange coinChange() {
		return new CoinChange(asList(1, 2, 5, 10, 20, 50, 100));
	}

}


