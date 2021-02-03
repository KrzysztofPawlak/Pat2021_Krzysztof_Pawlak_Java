package com.krzysztof.pawlak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        var reader = new BufferedReader(new InputStreamReader(System.in));
        var application = new Application();
        String input;
        System.out.println("Enter some data.");

        while (true) {
            input = reader.readLine();
            if (input.equals("q!")) {
                break;
            }
            application.execute(input);
        }
    }
}