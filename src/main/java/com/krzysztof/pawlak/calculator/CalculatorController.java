package com.krzysztof.pawlak.calculator;

import com.krzysztof.pawlak.models.Input;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.CalculatorSelector;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import java.util.Deque;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

@RestController
public class CalculatorController {

    private final CalculatorSelector calculatorSelector;
    private final InputConverter inputConverter;
    private final OutputConverter outputConverter;

    public CalculatorController(CalculatorSelector calculatorSelector,
                                InputConverter inputConverter,
                                OutputConverter outputConverter) {
        this.calculatorSelector = calculatorSelector;
        this.inputConverter = inputConverter;
        this.outputConverter = outputConverter;
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String add(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.ADD)));
    }

    @PostMapping("/subtract")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String subtract(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.SUBTRACT)));
    }

    @PostMapping("/multiply")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String multiply(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.MULTIPLY)));
    }

    @PostMapping("/divide")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String divide(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.DIVIDE)));
    }

    @PostMapping("/exponential")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object exponential(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.EXP)));
    }

    @PostMapping("/sqrt")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String sqrt(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.SQRT)));
    }

    @GetMapping("/info")
    public HttpEntity<byte[]> info() {
        byte[] output = calculatorSelector.makeFileInfo();
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(APPLICATION_OCTET_STREAM))
                .header("Content-Disposition", "attachment; filename=info.txt")
                .body(output);
    }
}