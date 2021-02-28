package com.krzysztof.pawlak.calculator;

import com.krzysztof.pawlak.models.Input;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.CalculatorSelector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Deque;

import static org.springframework.http.MediaType.*;


@Tag(name = "Calculator", description = "the Calculator API")
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
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4;4 5]\"]}"),
                            @ExampleObject(
                                    name = "An example 2 vectors.",
                                    value = "{\"values\":[\"[123 4]\",\"[11 2]\"]}"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Matrix Response", value = "[1 2; 3 4]"),
                                    @ExampleObject(name = "Vector Response", value = "[1 2]"),
                                    @ExampleObject(name = "Number Response", value = "1")})),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String add(@RequestBody @Valid Input input) {
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
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[1 2;3 1]\"]}"),
                            @ExampleObject(
                                    name = "An example 2 vectors.",
                                    value = "{\"values\":[\"[123 4]\",\"[11 2]\"]}"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Matrix Response", value = "[1 2; 3 4]"),
                                    @ExampleObject(name = "Vector Response", value = "[1 2]"),
                                    @ExampleObject(name = "Number Response", value = "1")})),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping("/subtract")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String subtract(@RequestBody @Valid Input input) {
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
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4;4 5]\"]}"),
                            @ExampleObject(
                                    name = "An example matrix and vector.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"[2 4]\"]}"),
                            @ExampleObject(
                                    name = "An example matrix and number.",
                                    value = "{\"values\":[\"[2 4;4 5]\",\"3\"]}"),
                            @ExampleObject(
                                    name = "An example vector and number.",
                                    value = "{\"values\":[\"[2 4]\",\"11.6\"]}"),
                            @ExampleObject(
                                    name = "An example 2 numbers.",
                                    value = "{\"values\":[\"123.21\",\"11.6\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Matrix Response", value = "[1 2; 3 4]"),
                                    @ExampleObject(name = "Vector Response", value = "[1 2]"),
                                    @ExampleObject(name = "Number Response", value = "1")})),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping("/multiply")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String multiply(@RequestBody @Valid Input input) {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.MULTIPLY)));
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
                                    value = "{\"values\":[\"4\",\"3\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Number Response", value = "64")
                            })),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping(value = "/exponential")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object exponential(@RequestBody @Valid Input input) {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.EXP)));
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
                                    value = "{\"values\":[\"200\",\"4\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Matrix Response", value = "[1 2; 3 4]"),
                                    @ExampleObject(name = "Vector Response", value = "[1 2]"),
                                    @ExampleObject(name = "Number Response", value = "1")})),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping("/divide")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String divide(@RequestBody @Valid Input input) {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.DIVIDE)));
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
                                    value = "{\"values\":[\"16\"]}")}))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "Number Response", value = "4")})),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @PostMapping("/sqrt")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String sqrt(@RequestBody @Valid Input input) {
        Deque<ValueContainer> values = inputConverter.convert(input);
        return outputConverter.convert(new ValueContainer(calculatorSelector.calculate(values, OperationChar.SQRT)));
    }

    @Operation(summary = "Get list of available operation for specific type")
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/octet-stream",
                    examples = {
                            @ExampleObject(name = "Operations info types response",
                                    value = "MATRIX - VECTOR\n" +
                                            " - MULTIPLY\n" +
                                            "2 NUMBERS\n" +
                                            " - ADD\n" +
                                            " - SUBTRACT\n" +
                                            " - MULTIPLY\n" +
                                            " - DIVIDE\n" +
                                            " - EXP\n" +
                                            "MATRIX - NUMBER\n" +
                                            " - MULTIPLY\n" +
                                            "NUMBER\n" +
                                            " - SQRT\n" +
                                            "MATRIX - MATRIX\n" +
                                            " - ADD\n" +
                                            " - SUBTRACT\n" +
                                            " - MULTIPLY\n" +
                                            "VECTOR - VECTOR\n" +
                                            " - ADD\n" +
                                            " - SUBTRACT\n" +
                                            "VECTOR - NUMBER\n" +
                                            " - MULTIPLY")}))
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