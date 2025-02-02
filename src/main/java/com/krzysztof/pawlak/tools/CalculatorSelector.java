package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByMatrixCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByNumberCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByVectorCalculator;
import com.krzysztof.pawlak.calculators.real.RealNumbersCalculator;
import com.krzysztof.pawlak.calculators.real.SqrtCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByNumberCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByVectorCalculator;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.history.HistoryOperation;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.validation.InputSizeValidator;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.Map;

import static com.krzysztof.pawlak.tools.CalculatorSelector.CalculatorEnum.*;


@Service
public class CalculatorSelector {

    private final HistoryOperation historyService;
    private final InputSizeValidator inputSizeValidator = new InputSizeValidator();
    private static final int MAX_SUPPORTED_INPUT_SIZE = 2;
    public static final int INPUT_SIZE_FOR_SQRT = 1;

    private static final Map<CalculatorEnum, Calculator> calculators = Map.ofEntries(
            Map.entry(MATRIX_MATRIX, new MatrixByMatrixCalculator()),
            Map.entry(MATRIX_VECTOR, new MatrixByVectorCalculator()),
            Map.entry(MATRIX_NUMBER, new MatrixByNumberCalculator()),
            Map.entry(VECTOR_VECTOR, new VectorByVectorCalculator()),
            Map.entry(VECTOR_NUMBER, new VectorByNumberCalculator()),
            Map.entry(REAL_NUMBERS, new RealNumbersCalculator()),
            Map.entry(SQRT, new SqrtCalculator())
    );

    public CalculatorSelector(HistoryOperation historyService) {
        this.historyService = historyService;
    }

    public enum CalculatorEnum {

        MATRIX_MATRIX("MATRIX - MATRIX"),
        MATRIX_VECTOR("MATRIX - VECTOR"),
        MATRIX_NUMBER("MATRIX - NUMBER"),
        VECTOR_VECTOR("VECTOR - VECTOR"),
        VECTOR_NUMBER("VECTOR - NUMBER"),
        REAL_NUMBERS("2 NUMBERS"),
        SQRT("NUMBER");

        private final String value;

        CalculatorEnum(String value) {
            this.value = value;
        }

        public String getAcceptedInput() {
            return this.value;
        }
    }

    private Calculator select(Deque<ValueContainer> deque) {
        final var value = deque.peekFirst();
        if (deque.size() == INPUT_SIZE_FOR_SQRT) {
            if (isNumber(value)) {
                return calculators.get(SQRT);
            } else {
                throw new CalculationNotImplementedException();
            }
        }
        if (deque.size() > MAX_SUPPORTED_INPUT_SIZE) {
            throw new CalculationNotImplementedException();
        }
        final var value2 = deque.peekLast();
        if (isNumberAndNumber(value, value2)) {
            return calculators.get(REAL_NUMBERS);
        }
        if (isMatrixAndMatrix(value, value2)) {
            return calculators.get(MATRIX_MATRIX);
        }
        if (isMatrixAndVector(value, value2)) {
            return calculators.get(MATRIX_VECTOR);
        }
        if (isMatrixAndNumber(value, value2)) {
            return calculators.get(MATRIX_NUMBER);
        }
        if (isVectorAndVector(value, value2)) {
            return calculators.get(VECTOR_VECTOR);
        }
        if (isVectorAndNumber(value, value2)) {
            return calculators.get(VECTOR_NUMBER);
        }
        throw new CalculationNotImplementedException();
    }

    private boolean isMatrixAndNumber(ValueContainer value, ValueContainer value2) {
        return (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.NUMBER) ||
                (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.MATRIX);
    }

    private boolean isMatrixAndVector(ValueContainer value, ValueContainer value2) {
        return (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.VECTOR) ||
                (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.MATRIX);
    }

    private boolean isMatrixAndMatrix(ValueContainer value, ValueContainer value2) {
        return value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.MATRIX;
    }

    private boolean isVectorAndVector(ValueContainer value, ValueContainer value2) {
        return value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.VECTOR;
    }

    private boolean isVectorAndNumber(ValueContainer value, ValueContainer value2) {
        return (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.NUMBER) ||
                (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.VECTOR);
    }

    private boolean isNumberAndNumber(ValueContainer value, ValueContainer value2) {
        return value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.NUMBER;
    }

    private boolean isNumber(ValueContainer value) {
        return value.getInputType() == InputType.NUMBER;
    }

    public Object calculate(Deque<ValueContainer> deque, OperationChar selected) {
        deque.forEach(inputSizeValidator::isValidThrowExceptionIfNot);
        final var calculator = select(deque);
        final var result = calculator.calculate(deque, selected);
        historyService.writeEntry(deque, new ValueContainer(result), selected.getRepresentation());
        return result;
    }

    public byte[] makeFileInfo() {
        final var stringBuffer = new StringBuilder();
        for (Map.Entry<CalculatorEnum, Calculator> entry : calculators.entrySet()) {
            stringBuffer.append(entry.getKey().getAcceptedInput());
            stringBuffer.append(System.lineSeparator());
            for (String suggestion : entry.getValue().suggest()) {
                stringBuffer.append(" - ").append(suggestion).append(System.lineSeparator());
            }
        }
        return stringBuffer.toString().getBytes();
    }
}