package blackjackanalyzer.input;

import blackjackanalyzer.gamedata.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputValidator {

    /**
     * Check all lines one at a time and if correct, add to a result list, then return the result list.
     * @param gameDataLines
     * @return List<String>
     */
    public List<String> checkAllGameDataLines(List<String> gameDataLines) {
        List<String> checkedList = new ArrayList<>();

        // Check each individual line using the checkLineBySizeAndContent method and add to a list if correct.
        for (String line : gameDataLines) {
            if (checkLineBySizeAndContent(line)) {
                checkedList.add(line);
            }
        }
        return checkedList;
    }

    /**
     * Check the line of string, so it matches the strict game data format.
     * @param line
     * @return Boolean
     */
    public Boolean checkLineBySizeAndContent(String line) {

        // Split the individual line of string and convert to a list.
        List<String> splitList = Arrays.asList(line.split(","));

        // Check that the list has exactly 6 elements.
        if (splitList.size() == 6) {
            boolean check = true;

            // Check for any blank element fields.
            for (String string : splitList) {
                if (string.isBlank()) {
                    check = false;
                }
            }
            return check;
        }
        return false;
    }


}
