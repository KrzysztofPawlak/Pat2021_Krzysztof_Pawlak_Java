package com.krzysztof.pawlak.history;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<String> list() {
        return historyService.getListOfFiles();
    }

    @GetMapping("/recent")
    public HttpEntity<byte[]> recent() {
        var output = historyService.readRecentHistoryFile();
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @GetMapping("/{filename}")
    public HttpEntity<byte[]> getByFile(@PathVariable String filename) {
        var output = historyService.readSpecificHistoryFile(filename);
        return ResponseEntity.ok()
                .contentLength(output.length)
                .contentType(new MediaType(TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(output);
    }

    @DeleteMapping
    public ResponseEntity delete() {
        return historyService.removeHistory() ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.CONFLICT);
    }
}