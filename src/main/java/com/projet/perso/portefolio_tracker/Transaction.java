package com.projet.perso.portefolio_tracker;

import java.time.LocalDate;

public record Transaction(
        String ticker,
        int quantity,
        double unitPrice,
        double brokerageFees,
        OperationType type,
        LocalDate date
) {}