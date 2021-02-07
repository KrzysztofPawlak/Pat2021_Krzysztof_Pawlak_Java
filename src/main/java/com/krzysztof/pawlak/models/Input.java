package com.krzysztof.pawlak.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static com.krzysztof.pawlak.config.AppConfig.ALLOWED_CHARS;

public class Input {

    private List<@NotNull @Pattern(
            regexp = ALLOWED_CHARS,
            message = "should be number or matrix: [1 2;3 4] or vector: [1 2 3]") String> values = new ArrayList<>();

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}