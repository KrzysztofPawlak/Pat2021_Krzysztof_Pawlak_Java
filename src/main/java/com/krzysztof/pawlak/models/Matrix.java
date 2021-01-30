package com.krzysztof.pawlak.models;

import java.math.BigDecimal;

public class Matrix {

    public void display(BigDecimal[][] matrix) {
        System.out.println();
        for (BigDecimal[] row : matrix) {
            for (BigDecimal value : row) {
                System.out.print(" " + value + " ");
            }
            System.out.println();
        }
    }
}