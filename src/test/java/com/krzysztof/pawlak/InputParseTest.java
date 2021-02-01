package com.krzysztof.pawlak;

import com.krzysztof.pawlak.tools.InputParse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputParseTest {

    private InputParse inputParse;

    @BeforeEach
    void setUp() {
        inputParse = new InputParse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "11.2", "-16", "[1 2 3 4]", "[1 2 3; 4 5 6; 7 8 10]", "[1 2 ;4 5]", "[1;2]",
            "[1 2 ; 4 5]", "[ 1 2 ; 4 5]", "[1 2 ; 4 5 ]"})
    void testInputValid(String input) {
        assertTrue(inputParse.isValid(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"[]", "--16", "+15", "[[1 2 3 4]", "[1 2 3;; 4 5 6; 7 8 10]", "[[1]", "[1]", "[1", " ",
            "[  1 2 3;4 5 6]", "[;1 2 3;4 5 6]", "[1 2 3;4 5 6;]", "[1 2 3] 4 5 6]", "[1 2 3]4 5 6]", "[1 2 3 4",
            "1 2 [3] 4", "1 2 [3 4", "[[1 2 3 4]]", "[1 2][1 2]", "15,2", "1 2 3"})
    void testInputInvalid(String input) {
        assertFalse(inputParse.isValid(input));
    }
}