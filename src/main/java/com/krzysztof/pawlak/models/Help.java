package com.krzysztof.pawlak.models;

import com.krzysztof.pawlak.config.AppConfig;

public class Help {

    public static void showSyntaxGuide() {
        System.out.println();
        System.out.println("# NUMBER #");
        System.out.println("Should be without any space. Decimal precision is also accepted.");
        System.out.println("examples:");
        System.out.println("1");
        System.out.println("1.0");
        System.out.println();
        System.out.println("# MATRIX #");
        System.out.println("Current setting allow calculate " + AppConfig.MAX_MATRIX_ROWS + "x" + AppConfig.MAX_MATRIX_COLUMNS + " size.");
        System.out.println("Should be surrounded by square brackets: [ values ]");
        System.out.println("examples:");
        System.out.println("[2 4;4 5]");
        System.out.println("one space between brackets [], number and column separator \";\" is also accepted");
        System.out.println("[ 2 4 ; 4 5 ]");
        System.out.println();
        System.out.println("# VECTOR #");
        System.out.println("Current setting allow calculate " + AppConfig.MAX_VECTOR_LENGTH + " length.");
        System.out.println("Should be surrounded by square brackets: [ values ]");
        System.out.println("examples:");
        System.out.println("row:");
        System.out.println("[ 2 4 5 ]");
        System.out.println("column:");
        System.out.println("[ 4 ; 5 ; 6 ]");
        System.out.println("one space between brackets [], number and column separator \";\" is also accepted");
        System.out.println();
    }

    public static void showOptions() {
        System.out.println("q! - exit");
        System.out.println("c - clear all memory");
        System.out.println("c1 - remove value from memory1");
        System.out.println("c2 - remove value from memory2");
        System.out.println("v - show current values in memory");
        System.out.println("h - show help");
        System.out.println("s - syntax guide");
        System.out.println("o - extended mode for sqrt (only available when one number in memory is present)");
    }
}