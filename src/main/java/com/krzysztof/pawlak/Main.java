package com.krzysztof.pawlak;

import com.krzysztof.pawlak.models.Matrix;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        String number = "1.21";
        System.out.println("vector: [1 2 3 4]");
        System.out.println("matrix: [1 2 3; 4 5 6; 7 8 10]");

        BigDecimal[][] matrix = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        Matrix matrixTest = new Matrix();
        matrixTest.display(matrix);

        try {
            Double.parseDouble(number);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}