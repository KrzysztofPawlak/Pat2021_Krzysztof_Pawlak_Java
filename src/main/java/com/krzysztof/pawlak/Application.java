package com.krzysztof.pawlak;

import com.krzysztof.pawlak.tools.CalculatorCoordinator;
import com.krzysztof.pawlak.tools.HUD;
import com.krzysztof.pawlak.models.Mode;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.InputParse;
import com.krzysztof.pawlak.tools.Suggester;

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
    private HUD hud;
    private Mode mode = Mode.INPUT;
    private CalculatorCoordinator coordinator;

    public Application(InputParse inputParse) {
        this.inputParse = inputParse;
        this.deque = new ArrayDeque();
        this.suggester = new Suggester();
        this.hud = new HUD();
        this.coordinator = new CalculatorCoordinator();
    }

    public void execute(String input) {
        try {
            if (deque.size() == 2 && mode == Mode.SELECTION) {
                ValueContainer valueContainer = new ValueContainer(coordinator.calculate(deque, 0));
                hud.printMem(valueContainer, deque.size());
                System.out.println("Too much arguments. You need remove one or both of them.");
            }
            if (mode == Mode.INPUT) {
                inputParse.isValidThrowException(input);
                Object object = inputParse.parse(input);
                ValueContainer valueContainer = new ValueContainer(object);
                deque.addLast(valueContainer);
                hud.printMem(valueContainer, deque.size());
            }
            if (deque.size() == 2) {
                mode = Mode.SELECTION;
                List<String> suggestions = suggester.suggest(deque);
                suggester.print(suggestions);
            }
            if (deque.size() == 1 && deque.peek().getInputType() == InputType.NUMBER) {
                List<String> suggestions = suggester.suggest(deque);
                suggester.print(suggestions);
            }

            if (mode == Mode.SELECTION || mode == Mode.INPUT) {
//                hud.showMemory(deque);
//                List<String> suggestions = suggester.suggest(deque);
//                suggester.print(suggestions);
            }
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