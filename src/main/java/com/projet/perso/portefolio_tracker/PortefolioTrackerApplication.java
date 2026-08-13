package com.projet.perso.portefolio_tracker;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class PortefolioTrackerApplication {

	public static void main(String[] args) {
		Transaction t1 = new Transaction("AAPL", 40, 100.0, 5.0, OperationType.BUY, LocalDate.now());
		Transaction t2 = new Transaction("AAPL", 30, 200.0, 5.0, OperationType.SELL, LocalDate.now());
		Position p = new Position();
		p.ticker = "AAPL";
		p.transactions = List.of(t1, t2);

		PortfolioService service = new PortfolioService();
		System.out.println("coût de revient " + service.calculateCostPrice(p));
		System.out.println("Position actuel " + service.calculateValorisation(p));
	}
}