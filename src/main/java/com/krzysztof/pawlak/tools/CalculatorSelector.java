package com.krzysztof.pawlak.tools;

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
import java.math.BigDecimal;
import java.util.Deque;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class CalculatorSelector {

    private static final MatrixByMatrixCalculator matrixByMatrixCalculator = new MatrixByMatrixCalculator();
    private static final MatrixByVectorCalculator matrixByVectorCalculator = new MatrixByVectorCalculator();
    private static final MatrixByNumberCalculator matrixByNumberCalculator = new MatrixByNumberCalculator();
    private static final VectorByVectorCalculator vectorByVectorCalculator = new VectorByVectorCalculator();
    private static final VectorByNumberCalculator vectorByNumberCalculator = new VectorByNumberCalculator();
    private static final RealNumbersCalculator realNumbersCalculator = new RealNumbersCalculator();
    private static final SqrtCalculator sqrtCalculator = new SqrtCalculator();

    public Object calculate(Deque<ValueContainer> deque, int selected) throws OperationNotSupportedException {

        var value = deque.peekFirst();
        if (deque.size() == 1 && value.getInputType() == InputType.NUMBER) {
            return sqrtCalculator.calculate((BigDecimal) value.getValue(), selected);
        }
        var value2 = deque.peekLast();
        if (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.NUMBER) {
            return realNumbersCalculator.calculate((BigDecimal) value.getValue(),
                    (BigDecimal) value2.getValue(), selected);
        }
        if (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.MATRIX) {
            return matrixByMatrixCalculator.calculate((BigDecimal[][]) value.getValue(),
                    (BigDecimal[][]) value2.getValue(), selected);
        }

        if (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.NUMBER) {
            return matrixByNumberCalculator.calculate((BigDecimal[][]) value.getValue(),
                    (BigDecimal) value2.getValue(), selected);
        }
        if (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.MATRIX) {
            return matrixByNumberCalculator.calculate((BigDecimal[][]) value2.getValue(),
                    (BigDecimal) value.getValue(), selected);
        }

        if (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.VECTOR) {
            return matrixByVectorCalculator.calculate((BigDecimal[][]) value.getValue(),
                    (Vector<BigDecimal>) value2.getValue(), selected);
        }
        if (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.MATRIX) {
            return matrixByVectorCalculator.calculate((BigDecimal[][]) value2.getValue(),
                    (Vector<BigDecimal>) value.getValue(), selected);
        }

        if (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.VECTOR) {
            return vectorByVectorCalculator.calculate((Vector<BigDecimal>) value.getValue(),
                    (Vector<BigDecimal>) value2.getValue(), selected);
        }

        if (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.NUMBER) {
            return vectorByNumberCalculator.calculate((Vector<BigDecimal>) value.getValue(),
                    (double) value2.getValue(), selected);
        }
        if (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.VECTOR) {
            return vectorByNumberCalculator.calculate((Vector<BigDecimal>) value2.getValue(),
                    (double) value.getValue(), selected);
        }
        throw new OperationNotSupportedException();
    }

    public void print(List<String> list) {
        System.out.println("[options]");
        IntStream.range(0, list.size()).forEach(index -> System.out.println(index + ". " + list.get(index)));
    }
}