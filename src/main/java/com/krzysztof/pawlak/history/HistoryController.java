package com.krzysztof.pawlak.history;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<String> recent() {
        return null;
    }

    @GetMapping("/{filename}")
    public List<String> getByFile() {
        return null;
    }

    @DeleteMapping
    public void delete() {

    }
}
