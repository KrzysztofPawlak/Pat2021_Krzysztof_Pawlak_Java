package com.krzysztof.pawlak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

//        BigDecimal[][] matrix = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
//        Matrix matrixTest = new Matrix();
//        matrixTest.display(matrix);

        var reader = new BufferedReader(new InputStreamReader(System.in));
        var inputParse = new InputParse();
        var application = new Application(inputParse);
        String input;
        System.out.println("type some data");

        while (true) {
            input = reader.readLine();
            if (input.equals("q!")) {
                break;
            }
            application.execute(input);
        }
    }
}