package com.krzysztof.pawlak;

import com.krzysztof.pawlak.calculators.matrix.MatrixByMatrixCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByNumberCalculator;
import com.krzysztof.pawlak.calculators.matrix.MatrixByVectorCalculator;
import com.krzysztof.pawlak.calculators.real.RealNumbersCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByNumberCalculator;
import com.krzysztof.pawlak.calculators.vector.VectorByVectorCalculator;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import javax.naming.OperationNotSupportedException;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

public class Suggester {

    private static final MatrixByMatrixCalculator matrixByMatrixCalculator = new MatrixByMatrixCalculator();
    private static final MatrixByVectorCalculator matrixByVectorCalculator = new MatrixByVectorCalculator();
    private static final MatrixByNumberCalculator matrixByNumberCalculator = new MatrixByNumberCalculator();
    private static final VectorByVectorCalculator vectorByVectorCalculator = new VectorByVectorCalculator();
    private static final VectorByNumberCalculator vectorByNumberCalculator = new VectorByNumberCalculator();
    private static final RealNumbersCalculator realNumbersCalculator = new RealNumbersCalculator();

    public List<String> suggest(Deque<ValueContainer> deque) throws OperationNotSupportedException {
        var value = deque.peekFirst();
        if (deque.size() == 1 && value.getInputType() == InputType.NUMBER) {
            return realNumbersCalculator.suggest();
        }
        var value2 = deque.peekLast();
        if (value.getInputType() == InputType.NUMBER && value.getInputType() == InputType.NUMBER) {
            return realNumbersCalculator.suggest();
        }
        if (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.MATRIX) {
            return matrixByMatrixCalculator.suggest();
        }
        if ((value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.NUMBER) ||
                (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.MATRIX)) {
            return matrixByNumberCalculator.suggest();
        }
        if ((value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.VECTOR) ||
                (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.MATRIX)) {
            return matrixByVectorCalculator.suggest();
        }
        if (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.VECTOR) {
            return vectorByVectorCalculator.suggest();
        }
        if ((value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.NUMBER) ||
                (value.getInputType() == InputType.NUMBER && value2.getInputType() == InputType.VECTOR)) {
            return vectorByNumberCalculator.suggest();
        }
        throw new OperationNotSupportedException();
    }

    public void print(List<String> list) {
        IntStream.range(0, list.size()).forEach(index -> System.out.println(index + ". " + list.get(index)));
    }
}