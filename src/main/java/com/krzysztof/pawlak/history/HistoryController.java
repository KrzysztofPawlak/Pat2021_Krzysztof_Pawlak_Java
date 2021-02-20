package com.krzysztof.pawlak.history;

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
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Tag(name = "History", description = "the History API")
@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryOperation historyService;

    public HistoryController(HistoryOperation historyService) {
        this.historyService = historyService;
    }

    @Operation(summary = "Get list of files log")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "List of files log",
                                            value = "[\"historia_obliczen.txt\"]")}))})
    @GetMapping
    public List<String> list() {
        return historyService.getListOfFiles();
    }

    @Operation(summary = "Get logs from recent file log")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(name = "List of recent operations",
                                            value = "4.0 ^ 3.0 = 64\n" +
                                            "[2.0 4.0;4.0 5.0] + [2.0 4.0;4.0 5.0] = [4.0 8.0;8.0 10.0]")}))})
    @GetMapping("/recent")
    public HttpEntity<byte[]> recent() {
        var output = historyService.readRecentHistoryFile();
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @Operation(summary = "Get history log by filename")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(name = "List of operations by specific file log",
                                            value = "4.0 ^ 3.0 = 64\n" +
                                            "[2.0 4.0;4.0 5.0] + [2.0 4.0;4.0 5.0] = [4.0 8.0;8.0 10.0]")})),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{\"description\": \"File not exists.\"}")}))})
    @GetMapping("/{filename}")
    public HttpEntity<byte[]> getByFile(@PathVariable String filename) {
        var output = historyService.readSpecificHistoryFile(filename);
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @Operation(summary = "Delete all files log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)})
    @DeleteMapping
    public ResponseEntity delete() {
        return historyService.removeHistory() ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.CONFLICT);
    }
}