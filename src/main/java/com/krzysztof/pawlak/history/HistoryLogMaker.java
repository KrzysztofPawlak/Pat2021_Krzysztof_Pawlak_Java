package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.calculator.OutputConverter;
import com.krzysztof.pawlak.models.ValueContainer;
import org.springframework.stereotype.Service;

import java.util.Deque;

import static com.krzysztof.pawlak.tools.CalculatorSelector.INPUT_SIZE_FOR_SQRT;

@Service
public class HistoryLogMaker {

    private final OutputConverter outputConverter;

    public HistoryLogMaker() {
        this.outputConverter = new OutputConverter();
    }

    public String createEntryString(Deque<ValueContainer> inputElements, ValueContainer result, String operator) {
        final var stringBuffer = new StringBuilder();
        if (inputElements.size() == INPUT_SIZE_FOR_SQRT) {
            stringBuffer.append(asText(operator));
            stringBuffer.append(asText(inputElements.peekFirst()));
        } else {
            stringBuffer.append(asText(inputElements.peekFirst()));
            stringBuffer.append(asText(operator));
            stringBuffer.append(asText(inputElements.peekLast()));
        }
        stringBuffer.append(asText("="));
        stringBuffer.append(asText(result));
        return stringBuffer.toString();
    }

    private String asText(ValueContainer value) {
        return outputConverter.convert(value);
    }

    private String asText(String operator) {
        return " " + operator + " ";
    }
}