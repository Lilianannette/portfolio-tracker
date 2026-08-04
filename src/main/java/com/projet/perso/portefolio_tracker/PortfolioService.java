package com.projet.perso.portefolio_tracker;

public class PortfolioService {
    public double calculateCostPrice(Position position) {
        double totalCost = 0;
        int quantityBuy = 0;
        for (Transaction t : position.transactions ) {
            if(t.type() == OperationType.BUY) {
                totalCost = totalCost + t.quantity() * t.unitPrice() + t.brokerageFees();
                quantityBuy = quantityBuy + t.quantity();
            }
        }
        return totalCost / quantityBuy;
    }

    public double calculateValorisation(Position position) {
        int quantityBuy = 0;
        double marketPrice = 95.0;

        for (Transaction t : position.transactions ) {
            if(t.type() == OperationType.PENDING) {
                quantityBuy = quantityBuy + t.quantity();
            }
        }
        return quantityBuy * marketPrice;
    }
}