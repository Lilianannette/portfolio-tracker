package com.projet.perso.portefolio_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class PortefolioTrackerApplication {

	public static void main(String[] args) {
		Transaction t1 = new Transaction("AAPL", 10, 100.0, 5.0, OperationType.BUY, LocalDate.now());
		Transaction t2 = new Transaction("AAPL", 10, 100.0, 5.0, OperationType.PENDING, LocalDate.now());
		Transaction t3 = new Transaction("AAPL", 30, 200.0, 5.0, OperationType.BUY, LocalDate.now());
		Transaction t4 = new Transaction("AAPL", 0, 0, 0, OperationType.BUY, LocalDate.now());
		Position p = new Position();
		p.ticker = "AAPL";
		p.transactions = List.of(t1, t2, t3);
//		p.transactions = List.of(t3);

		PortfolioService service = new PortfolioService();
		System.out.println("coût de revient " + service.calculateCostPrice(p));
		System.out.println("Position actuel " + service.calculateValorisation(p));
	}
}


//Transaction t3 = new Transaction("AAPL", 0, 0, 0, OperationType.BUY, LocalDate.now());
//coût de revient NaN
