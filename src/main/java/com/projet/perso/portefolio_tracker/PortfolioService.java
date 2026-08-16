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
        int quantityBought = 0;
        int quantitySold = 0;
        double marketPrice = 95.0;
        int quantityHeld = 0;

        for (Transaction t : position.transactions) {
            if (t.type() == OperationType.BUY) {
                quantityBought += t.quantity();
            }
            if (t.type() == OperationType.SELL) {
                quantitySold += t.quantity();
            }
            quantityHeld = quantityBought - quantitySold;
        }
        return quantityHeld * marketPrice;
    }

    // Duplication
    public double calculateLatenteGain(Position position) {
        double currentPrice = 95;
        int quantitySold = 0;
        int quantityBought = 0;
        int quantityHeld = 0;
        double buyCost = calculateCostPrice(position);
        for (Transaction t : position.transactions) {
            if (t.type() == OperationType.BUY) {
                quantityBought += t.quantity();
            }
            if (t.type() == OperationType.SELL) {
                quantitySold += t.quantity();
            }
            quantityHeld = quantityBought - quantitySold;
        }
        return quantityHeld * (currentPrice - buyCost);
    }

    public double calculateRealizedGain(Position position) {
        double realizedGain = 0;
        double buyCost = calculateCostPrice(position);
        for (Transaction t : position.transactions) {
            if (t.type() == OperationType.SELL) {
                realizedGain += (t.unitPrice() - buyCost) * t.quantity();
            }
        }
        return realizedGain;
    }
}