package com.krzysztof.pawlak;

import com.krzysztof.pawlak.models.ValueContainer;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Vector;

public class Application {

    private final InputParse inputParse;
    private Deque<ValueContainer> deque;
    private Suggester suggester;

    public Application(InputParse inputParse) {
        this.inputParse = inputParse;
        this.deque = new ArrayDeque();
        this.suggester = new Suggester();
    }

    public void execute(String input) {
        try {
            inputParse.isValidThrowException(input);
            Object object = inputParse.parse(input);
            ValueContainer valueContainer = new ValueContainer(object);
            deque.addLast(valueContainer);
            Object pop = deque.peek();
            displayInputType(pop);
            List<String> suggestions = suggester.suggest(deque);
            suggester.print(suggestions);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
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