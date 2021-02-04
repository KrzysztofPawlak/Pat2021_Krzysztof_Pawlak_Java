package com.krzysztof.pawlak;

import com.krzysztof.pawlak.models.Help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.krzysztof.pawlak.models.Help.showOptions;

public class Main {

    public static void main(String[] args) throws IOException {

        var reader = new BufferedReader(new InputStreamReader(System.in));
        var application = new Application();
        String input;
        System.out.println("Welcome in Krzysztof Calculator");
        showOptions();
        System.out.println("Please enter some data...");

        while (true) {
            input = reader.readLine();
            if (input.equals("q!")) {
                break;
            }
            application.execute(input);
        }
    }
}