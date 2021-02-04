package com.krzysztof.pawlak;

import com.krzysztof.pawlak.config.AppConfig;
import com.krzysztof.pawlak.models.CalculationMode;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.Mode;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.CalculatorSelector;
import com.krzysztof.pawlak.tools.HUD;
import com.krzysztof.pawlak.tools.InputParse;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.krzysztof.pawlak.models.Help.showOptions;
import static com.krzysztof.pawlak.models.Help.showSyntaxGuide;

public class Application {

    private final InputParse inputParse;
    private final Deque<ValueContainer> deque;
    private final HUD hud;
    private Mode mode = Mode.INPUT;
    private CalculationMode calculationMode = CalculationMode.NORMAL;
    private final CalculatorSelector calculatorSelector;
    private static final int MAX_MEMORY_SLOT = 2;
    private static final int ELEMENTS_IN_MEMORY_FOR_EXTENDED_MODE = 1;
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public Application() {
        this.inputParse = new InputParse();
        this.deque = new ArrayDeque<>();
        this.hud = new HUD();
        this.calculatorSelector = new CalculatorSelector();
//        LogManager.getLogManager().reset();
    }

    public void execute(String input) {
        try {
            if (handleIfAdditionalOptionIsSelected(input)) {
                return;
            }
            shouldSwitchToExtendedMode(input);
            if (calculationMode == CalculationMode.EXTENDED) {
                handleExtendedMode(input);
            } else {
                handleNormalMode(input);
            }
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private boolean handleIfAdditionalOptionIsSelected(String input) throws OperationNotSupportedException {
        switch (input) {
            case "c1":
                if (deque.peekFirst() != null) {
                    deque.removeFirst();
                    mode = Mode.INPUT;
                }
                suggestEnterData();
                return true;
            case "c2":
                if (deque.peekLast() != null) {
                    deque.removeLast();
                    mode = Mode.INPUT;
                }
                suggestEnterData();
                return true;
            case "c":
                if (!deque.isEmpty()) {
                    deque.clear();
                    mode = Mode.INPUT;
                }
                suggestEnterData();
                return true;
            case "h":
                showOptions();
                suggestEnterData();
                return true;
            case "s":
                showSyntaxGuide();
                suggestEnterData();
                return true;
            case "v":
                if (!deque.isEmpty()) {
                    hud.showMemory(deque);
                } else {
                    System.out.println("Memory is empty.");
                }
                suggestEnterData();
                return true;
            default:
                return false;
        }
    }

    private void validInputSizeThrowException(ValueContainer value) {
        if (value.getInputType() == InputType.MATRIX) {
            final int rows = ((BigDecimal[][]) value.getValue()).length;
            final int columns = ((BigDecimal[][]) value.getValue())[0].length;
            if (rows > AppConfig.MAX_MATRIX_ROWS || columns > AppConfig.MAX_MATRIX_COLUMNS) {
                throw new IllegalArgumentException("Sorry, max supported matrix size is: " +
                        AppConfig.MAX_MATRIX_ROWS + "x" + AppConfig.MAX_MATRIX_COLUMNS + ".");
            }
        }
        if (value.getInputType() == InputType.VECTOR) {
            final int size = ((Vector<BigDecimal>) value.getValue()).size();
            if (size > AppConfig.MAX_VECTOR_LENGTH) {
                throw new IllegalArgumentException("Sorry, max supported vector length is: " +
                        AppConfig.MAX_VECTOR_LENGTH + ".");
            }
        }
    }

    private void handleNormalMode(String input) throws OperationNotSupportedException {
        if (mode == Mode.OPTION_SELECTED) {
            LOGGER.log(Level.INFO, "OPTION_SELECTED");
            final int option = getOption(input);
            final var valueContainer = calculate(option);
            hud.printElementFromMemory(valueContainer, deque.size());
            mode = Mode.INPUT;
            suggestEnterData();
            return;
        }
        if (mode == Mode.INPUT) {
            LOGGER.log(Level.INFO, "INPUT");
            addDataToMemory(input);
        }
        suggestEnterData();
    }

    private void handleExtendedMode(String input) throws OperationNotSupportedException {
        if (mode == Mode.SELECTION) {
            LOGGER.log(Level.INFO, "SELECTION");
            suggestOptions();
            return;
        }
        if (mode == Mode.OPTION_SELECTED) {
            LOGGER.log(Level.INFO, "OPTION_SELECTED");
            final int option = getOption(input);
            final var valueContainer = calculate(option);
            hud.printElementFromMemory(valueContainer, deque.size());
            mode = Mode.INPUT;
            calculationMode = CalculationMode.NORMAL;
            suggestEnterData();
            return;
        }
        if (mode == Mode.INPUT) {
            LOGGER.log(Level.INFO, "INPUT");
            addDataToMemory(input);
        }
    }

    private int returnOptionIfValid(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You should select one operation from list or " +
                    "clear current values from memory by typing: c / c1 / c2.");
        }
    }

    private void suggestEnterData() throws OperationNotSupportedException {
        if (deque.size() == 1 && deque.peek().getInputType() == InputType.NUMBER) {
            System.out.println("Enter some data or switch to extended mode (SQRT) typing: \"o\".");
        }
        if (deque.size() == MAX_MEMORY_SLOT) {
            LOGGER.log(Level.INFO, "MAX_MEMORY_SLOT");
            suggestOptions();
        }
        if (deque.size() == 1 && deque.peek().getInputType() != InputType.NUMBER) {
            System.out.println("Enter some data.");
        }
        if (deque.isEmpty()) {
            System.out.println("Enter some data.");
        }
    }

    private void suggestOptions() throws OperationNotSupportedException {
        final List<String> suggestions = calculatorSelector.suggest(deque);
        hud.printSuggestions(suggestions);
        mode = Mode.OPTION_SELECTED;
        System.out.println("Select one of the displayed options.");
    }

    private ValueContainer calculate(int option) throws OperationNotSupportedException {
        final var valueContainer = new ValueContainer(calculatorSelector.calculate(deque, option));
        deque.clear();
        deque.add(valueContainer);
        return valueContainer;
    }

    private int getOption(String input) throws OperationNotSupportedException {
        LOGGER.log(Level.INFO, "parse option to selected option number");
        final int option = returnOptionIfValid(input);
        if (option > calculatorSelector.suggest(deque).size() || option < 1) {
            throw new IllegalArgumentException("Selected option is not available. Enter again.");
        }
        return option;
    }

    private void addDataToMemory(String input) {
        LOGGER.log(Level.INFO, "addDataToMemory");
        inputParse.isValidSyntaxThrowException(input);
        final var object = inputParse.parse(input);
        final var valueContainer = new ValueContainer(object);
        validInputSizeThrowException(valueContainer);
        deque.addLast(valueContainer);
        hud.printElementFromMemory(valueContainer, deque.size());
    }

    public void shouldSwitchToExtendedMode(String input) {
        final boolean isExtendedMode = deque.size() == ELEMENTS_IN_MEMORY_FOR_EXTENDED_MODE &&
                deque.peek().getInputType() == InputType.NUMBER && input.equals("o");
        if (isExtendedMode) {
            calculationMode = CalculationMode.EXTENDED;
            mode = Mode.SELECTION;
        }
    }
}