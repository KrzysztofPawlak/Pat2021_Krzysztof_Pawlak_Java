package com.krzysztof.pawlak.config;

import com.krzysztof.pawlak.history.*;
import com.krzysztof.pawlak.tools.FileLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureConfig {

    private final HistoryLogMaker historyLogMaker;

    public FeatureConfig(HistoryLogMaker historyLogMaker) {
        this.historyLogMaker = historyLogMaker;
    }

    @Bean
    @ConditionalOnProperty(name = "H2_STORAGE_ENABLED", havingValue = "false")
    @Autowired
    public HistoryOperation historyFileService(FileLoaderService fileLoaderService) {
        return new HistoryService(fileLoaderService, historyLogMaker);
    }

    @Bean
    @ConditionalOnProperty(name = "H2_STORAGE_ENABLED", havingValue = "true")
    @Autowired
    public HistoryOperation historyH2Service(HistoryRepository historyRepository) {
        return new H2HistoryService(historyRepository, historyLogMaker);
    }
}