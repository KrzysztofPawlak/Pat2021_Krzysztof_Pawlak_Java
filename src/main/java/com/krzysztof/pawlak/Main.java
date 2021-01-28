package com.krzysztof.pawlak;

public class Main {

    public static void main(String[] args) {

        String number = "1.21";
        System.out.println("vector: [1 2 3 4]");
        System.out.println("matrix: [1 2 3; 4 5 6; 7 8 10]");

        try {
            Double.parseDouble(number);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}