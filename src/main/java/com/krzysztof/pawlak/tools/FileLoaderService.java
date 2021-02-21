package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.error.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Component
public class FileLoaderService {

    public static final String CURRENT_DIR_WITH_LOGS_PROPERTY = "user.dir";
    Logger logger = LoggerFactory.getLogger(FileLoaderService.class);

    public byte[] getFileAsByteArr(String filename) {
        if (!exists(filename)) {
            throw new ResourceNotFoundException("File not exists.");
        }
        try {
            return Files.readAllBytes(getPath(filename));
        } catch (IOException e) {
            logger.error("getFileAsByteArr() - no file: ".concat(filename));
            throw new ResourceNotFoundException("File not exists.");
        }
    }

    public static boolean exists(String filename) {
        return Files.exists(getPath(filename));
    }

    public static int countLine(String filename) {
        try (Stream<String> stream = Files.lines(getPath(filename),
                StandardCharsets.UTF_8)) {
            return Math.toIntExact(stream.count());
        } catch (IOException e) {
            return 0;
        }
    }

    public static Path getPath(String filename) {
        return Paths.get(System.getProperty(CURRENT_DIR_WITH_LOGS_PROPERTY), filename);
    }

    public static Path getDirPath() {
        return Paths.get(System.getProperty(CURRENT_DIR_WITH_LOGS_PROPERTY));
    }
}