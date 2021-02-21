package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.Range;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Tag(name = "History", description = "the History API")
@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryOperation historyService;

    public HistoryController(HistoryOperation historyService) {
        this.historyService = historyService;
    }

    @Operation(summary = "Get logs by range")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "List of operations",
                                            value = "4.0 ^ 3.0 = 64\n" +
                                                    "[2.0 4.0;4.0 5.0] + [2.0 4.0;4.0 5.0] = [4.0 8.0;8.0 10.0]")})),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)})
    @GetMapping
    public HttpEntity<byte[]> read(@Valid Range range) {
        final var output = historyService.readByRange(range);
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @Operation(summary = "Get recent logs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "*/*",
                            examples = {
                                    @ExampleObject(name = "List of recent operations",
                                            value = "4.0 ^ 3.0 = 64\n" +
                                                    "[2.0 4.0;4.0 5.0] + [2.0 4.0;4.0 5.0] = [4.0 8.0;8.0 10.0]")})),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping("/recent")
    public HttpEntity<byte[]> recent() {
        var output = historyService.readRecent();
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @Operation(summary = "Delete all logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)})
    @DeleteMapping
    public ResponseEntity delete() {
        return historyService.removeHistory() ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.CONFLICT);
    }
}