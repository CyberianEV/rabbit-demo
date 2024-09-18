package utils;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    public static List<String> getTwoArgsResult(String input) {
        if (!input.contains(" ")) {
            throw new IllegalArgumentException("--- Illegal input, try again ---");
        }
        int spaceIndex = input.indexOf(" ");
        List<String> result = new ArrayList<>();
        result.add(input.substring(0, spaceIndex).trim());
        result.add(input.substring(spaceIndex + 1));
        return result;
    }
}
