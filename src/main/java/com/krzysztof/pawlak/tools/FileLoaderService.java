package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.error.FileNotExistException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileLoaderService {

    public byte[] getFileAsByteArr(Path filePath) {
        if (!exists(filePath)) {
            throw new FileNotExistException();
        }
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new FileNotExistException();
        }
    }

    private boolean exists(Path filePath) {
        return Files.exists(filePath);
    }
}