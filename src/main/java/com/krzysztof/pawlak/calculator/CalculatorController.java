package com.krzysztof.pawlak.calculator;

import com.krzysztof.pawlak.models.Input;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.CalculatorSelector;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import java.util.Deque;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;


@Api(tags = "calculator")
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

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Adding two values.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example 2 matrices.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4;4 5]\"]}",
                                    summary = "matrix - matrix"),
                            @ExampleObject(
                                    name = "An example 2 vectors.",
                                    value = "{\"values\":[\"[123 4]\",\"[11 2]\"]}",
                                    summary = "vector - vector"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}",
                                    summary = "number - number")}))
    @ApiOperation(value = "Adding two values.")
    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String add(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.ADD)));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Subtract two values.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example 2 matrices.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[1 2;3 1]\"]}",
                                    summary = "matrix - matrix"),
                            @ExampleObject(
                                    name = "An example 2 vectors.",
                                    value = "{\"values\":[\"[123 4]\",\"[11 2]\"]}",
                                    summary = "vector - vector"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}",
                                    summary = "number - number")}))
    @ApiOperation(value = "Subtract two values.")
    @PostMapping("/subtract")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String subtract(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.SUBTRACT)));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Multiply two values.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example 2 matrices.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4;4 5]\"]}",
                                    summary = "matrix - matrix"),
                            @ExampleObject(
                                    name = "An example matrix and vector.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4]\"]}",
                                    summary = "matrix - vector"),
                            @ExampleObject(
                                    name = "An example matrix and number.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"3\"]}",
                                    summary = "matrix - number"),
                            @ExampleObject(
                                    name = "An example vector and number.",
                                    value = "{\"values\":[\"[2 4]\",\"11.6\"]}",
                                    summary = "vector - number"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}",
                                    summary = "2 numbers")}))
    @ApiOperation(value = "Multiply two values.")
    @PostMapping("/multiply")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String multiply(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.MULTIPLY)));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Divide two values.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"200\",\"4\"]}",
                                    summary = "2 numbers")}))
    @ApiOperation(value = "Divide two values.")
    @PostMapping("/divide")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String divide(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.DIVIDE)));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exponential two values.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"4\",\"3\"]}",
                                    summary = "2 numbers")}))
    @ApiOperation(value = "Exponential two values.")
    @PostMapping("/exponential")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object exponential(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.EXP)));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sqrt from value.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Input.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "An example number.",
                                    value = "{\"values\":[\"16\"]}",
                                    summary = "number")}))
    @ApiOperation(value = "Sqrt from value.")
    @PostMapping("/sqrt")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String sqrt(@RequestBody @Valid Input input) throws OperationNotSupportedException {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.SQRT)));
    }

    @ApiOperation(value = "List of all operations by input type.")
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