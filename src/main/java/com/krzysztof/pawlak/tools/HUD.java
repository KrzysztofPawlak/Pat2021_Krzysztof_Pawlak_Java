package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Deque;
import java.util.Vector;

public class HUD {

    public void showMemory(Deque<ValueContainer> deque) {
        if (deque.isEmpty()) {
            return;
        }
        var value = deque.peekFirst();
        printElementFromMemory(value, 1);
        if (deque.size() == 1) {
            return;
        }
        var value2 = deque.peekLast();
        printElementFromMemory(value2, 2);
    }

    public void printElementFromMemory(ValueContainer value, int memoryNumber) {
        System.out.println("# memory" + memoryNumber + " #");
        show(value);
    }

    private void show(ValueContainer value) {
        if (value.getInputType() == InputType.MATRIX) {
            display((BigDecimal[][]) value.getValue());
        }
        if (value.getInputType() == InputType.VECTOR) {
            display((Vector) value.getValue());
        }
        if (value.getInputType() == InputType.NUMBER) {
            System.out.println(" " + value.getValue() + " ");
        }
    }

    private void display(BigDecimal[][] matrix) {
        for (BigDecimal[] row : matrix) {
            for (BigDecimal value : row) {
                System.out.print(" " + value + " ");
            }
            System.out.println();
        }
    }

    private void display(Vector vector) {
        Arrays.stream(vector.toArray()).forEach(value -> System.out.print(" " + value + " "));
        System.out.println();
    }
}