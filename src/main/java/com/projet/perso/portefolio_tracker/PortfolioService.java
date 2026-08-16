package com.projet.perso.portefolio_tracker;

public class PortfolioService {
    public double calculateCostPrice(Position position) {
        double totalCost = 0;
        int quantityBought = 0;
        for (Transaction t : position.transactions ) {
            if(t.type() == OperationType.BUY) {
                totalCost = totalCost + t.quantity() * t.unitPrice() + t.brokerageFees();
                // DUPLICATION partielle : même squelette de parcours + filtre BUY que les autres méthodes
                quantityBought += t.quantity();
            }
        }
        return totalCost / quantityBought;
    }

    public double calculateValorisation(Position position) { // DUPLICATION : ce bloc "quantité détenue" est identique dans calculateValorisation
        int quantityBought = 0;
        int quantitySold = 0;
        double marketPrice = 95.0;
        int quantityHeld = 0;
        for (Transaction t : position.transactions) {
            if (t.type() == OperationType.BUY) {
                // DUPLICATION partielle : même squelette de parcours + filtre BUY que les autres méthodes
                quantityBought += t.quantity();
            }
            if (t.type() == OperationType.SELL) {
                quantitySold += t.quantity();
            }
            quantityHeld = quantityBought - quantitySold;
        }
        return quantityHeld * marketPrice;
    }

    public double calculateLatenteGain(Position position) { // DUPLICATION : ce bloc "quantité détenue" est identique dans calculateValorisation
        double marketPrice = 95;
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
        return quantityHeld * (marketPrice - buyCost);
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