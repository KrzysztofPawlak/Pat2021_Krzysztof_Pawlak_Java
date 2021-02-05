package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByMatrixCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByNumberCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByVectorCalculator;
import com.krzysztof.pawlak.calculators.real.RealNumbersCalculator;
import com.krzysztof.pawlak.calculators.real.SqrtCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByNumberCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByVectorCalculator;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import javax.naming.OperationNotSupportedException;
import java.util.Deque;
import java.util.List;

public class CalculatorSelector {

    private static final MatrixByMatrixCalculator matrixByMatrixCalculator = new MatrixByMatrixCalculator();
    private static final MatrixByVectorCalculator matrixByVectorCalculator = new MatrixByVectorCalculator();
    private static final MatrixByNumberCalculator matrixByNumberCalculator = new MatrixByNumberCalculator();
    private static final VectorByVectorCalculator vectorByVectorCalculator = new VectorByVectorCalculator();
    private static final VectorByNumberCalculator vectorByNumberCalculator = new VectorByNumberCalculator();
    private static final RealNumbersCalculator realNumbersCalculator = new RealNumbersCalculator();
    private static final SqrtCalculator sqrtCalculator = new SqrtCalculator();

    private Calculator select(Deque<ValueContainer> deque) throws OperationNotSupportedException {

        final var value = deque.peekFirst();
        if (deque.size() == 1) {
            if (isNumber(value)) {
                return sqrtCalculator;
            } else {
                throw new OperationNotSupportedException();
            }
        }
        final var value2 = deque.peekLast();
        if (isNumberAndNumber(value, value2)) {
            return realNumbersCalculator;
        }
        if (isMatrixAndMatrix(value, value2)) {
            return matrixByMatrixCalculator;
        }
        if (isMatrixAndVector(value, value2)) {
            return matrixByVectorCalculator;
        }
        if (isMatrixAndNumber(value, value2)) {
            return matrixByNumberCalculator;
        }
        if (isVectorAndVector(value, value2)) {
            return vectorByVectorCalculator;
        }
        if (isVectorAndNumber(value, value2)) {
            return vectorByNumberCalculator;
        }
        throw new OperationNotSupportedException();
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

    public Object calculate(Deque<ValueContainer> deque, int selected) throws OperationNotSupportedException {
        final var calculator = select(deque);
        return calculator.calculate(deque, selected);
    }

    public List<String> suggest(Deque<ValueContainer> deque) throws OperationNotSupportedException {
        final var calculator = select(deque);
        return calculator.suggest();
    }

    public String getOperationByPosition(Deque<ValueContainer> deque, int position) throws OperationNotSupportedException {
        final var calculator = select(deque);
        return calculator.getOperationNameAsString(position);
    }
}