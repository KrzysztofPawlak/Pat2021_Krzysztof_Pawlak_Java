package com.krzysztof.pawlak;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.Vector;

public class Application {

    private final InputParse inputParse;
    private final Stack stack;

    public Application(InputParse inputParse) {
        this.inputParse = inputParse;
        this.stack = new Stack();
    }

    public void execute(String input) {
        try {
            inputParse.isValidThrowException(input);
            Object object = inputParse.parse(input);
            stack.push(object);
            Object pop = stack.pop();
            displayInputType(pop);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        // TODO
        // inputParse.isValid(input);
        // displayMessageIfNeeded();
        // var currentValue = determineInputType();
        // var previousValue = stack.peek();
        // Operation operation = suggestOptions(stack);
        // calculate(operation, stack);
        // printResult();
    }

    private void displayInputType(Object pop) {
        if (pop instanceof BigDecimal) {
            System.out.println("is big decimal");
        } else if (pop instanceof Vector) {
            System.out.println("is vector");
        } else if (pop instanceof BigDecimal[][]) {
            System.out.println("is matrix");
        }
    }
}