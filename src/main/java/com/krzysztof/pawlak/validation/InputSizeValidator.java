package com.krzysztof.pawlak.validation;

import com.krzysztof.pawlak.config.AppConfig;
import com.krzysztof.pawlak.error.CalculationConstrainException;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.Vector;

public class InputSizeValidator {

    public void isValidThrowExceptionIfNot(ValueContainer value) {
        if (value.getInputType() == InputType.MATRIX) {
            final int rows = ((BigDecimal[][]) value.getValue()).length;
            final int columns = ((BigDecimal[][]) value.getValue())[0].length;
            if (rows > AppConfig.MAX_MATRIX_ROWS || columns > AppConfig.MAX_MATRIX_COLUMNS) {
                throw new CalculationConstrainException("Sorry, max supported matrix size is: " +
                        AppConfig.MAX_MATRIX_ROWS + "x" + AppConfig.MAX_MATRIX_COLUMNS + ".");
            }
        }
        if (value.getInputType() == InputType.VECTOR) {
            final int size = ((Vector<BigDecimal>) value.getValue()).size();
            if (size > AppConfig.MAX_VECTOR_LENGTH) {
                throw new CalculationConstrainException("Sorry, max supported vector length is: " +
                        AppConfig.MAX_VECTOR_LENGTH + ".");
            }
        }
    }
}