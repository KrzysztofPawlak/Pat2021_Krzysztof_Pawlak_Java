package com.krzysztof.pawlak;

import com.krzysztof.pawlak.config.AppConfig;
import com.krzysztof.pawlak.models.*;
import com.krzysztof.pawlak.tools.CalculatorSelector;
import com.krzysztof.pawlak.tools.HUD;
import com.krzysztof.pawlak.tools.HistoryWriter;
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
    private final HistoryWriter historyWriter;

    public Application() {
        this.inputParse = new InputParse();
        this.deque = new ArrayDeque<>(MAX_MEMORY_SLOT);
        this.hud = new HUD();
        this.calculatorSelector = new CalculatorSelector();
        this.historyWriter = new HistoryWriter();
        loggerOnOff();
    }

    public void execute(String input) {
        try {
            if (handleIfAdditionalOptionIsSelected(input)) {
                suggestEnterData();
                return;
            }
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

    private boolean handleIfAdditionalOptionIsSelected(String input) {
        switch (input) {
            case "c1":
                removeFirstElementFromMemoryIfNeeded();
                return true;
            case "c2":
                removeLastElementFromMemoryIfNeeded();
                return true;
            case "c":
                clearMemoryIfNeeded();
                return true;
            case "h":
                showOptions();
                return true;
            case "s":
                showSyntaxGuide();
                return true;
            case "v":
                showCurrentMemoryIfNeeded();
                return true;
            case "o":
                if (shouldSwitchToExtendedMode()) {
                    switchToExtendedMode();
                    return false;
                }
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
    }

    private ValueContainer calculate(int option) throws OperationNotSupportedException {
        final var valueContainer = new ValueContainer(calculatorSelector.calculate(deque, option));
        final var operation = calculatorSelector.getOperationByPosition(deque, option);
        historyWriter.writeEntry(deque, valueContainer, OperationChar.valueOf(operation).getRepresentation());
        deque.clear();
        deque.add(valueContainer);
        return valueContainer;
    }

    private int getOption(String input) throws OperationNotSupportedException {
        LOGGER.log(Level.INFO, "parse option to selected option number");
        final int option = returnOptionIfValid(input);
        if (isRangeAccepted(option)) {
            throw new IllegalArgumentException("Selected option is not available. Enter again.");
        }
        return option;
    }

    private boolean isRangeAccepted(int option) throws OperationNotSupportedException {
        return option > calculatorSelector.suggest(deque).size() || option < 1;
    }

    private void addDataToMemory(String input) {
        LOGGER.log(Level.INFO, "addDataToMemory");
        inputParse.isValidSyntaxThrowException(input);
        final var object = inputParse.parse(input);
        final var valueContainer = new ValueContainer(object);
        validInputSizeThrowException(valueContainer);
        addToMemoryIfPossible(valueContainer);
        hud.printElementFromMemory(valueContainer, deque.size());
    }

    private void addToMemoryIfPossible(ValueContainer valueContainer) {
        if (deque.size() < MAX_MEMORY_SLOT) {
            deque.addLast(valueContainer);
        }
    }

    private boolean shouldSwitchToExtendedMode() {
        return deque.size() == ELEMENTS_IN_MEMORY_FOR_EXTENDED_MODE && deque.peek().getInputType() == InputType.NUMBER;
    }

    private void switchToExtendedMode() {
        calculationMode = CalculationMode.EXTENDED;
        mode = Mode.SELECTION;
    }

    private void removeFirstElementFromMemoryIfNeeded() {
        if (deque.peekFirst() != null) {
            deque.removeFirst();
            System.out.println("memory1 cleared");
            mode = Mode.INPUT;
        }
    }

    private void removeLastElementFromMemoryIfNeeded() {
        if (deque.peekLast() != null && deque.size() > 1) {
            deque.removeLast();
            System.out.println("memory2 cleared");
            mode = Mode.INPUT;
        }
    }

    private void clearMemoryIfNeeded() {
        if (!deque.isEmpty()) {
            deque.clear();
            System.out.println("memory cleared");
            mode = Mode.INPUT;
        }
    }

    private void showCurrentMemoryIfNeeded() {
        hud.showMemory(deque);
    }

    private void loggerOnOff() {
        if (!AppConfig.IS_LOGGER_ON)
            LogManager.getLogManager().reset();
    }
}