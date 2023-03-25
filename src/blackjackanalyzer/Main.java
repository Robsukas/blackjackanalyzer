package blackjackanalyzer;

import blackjackanalyzer.gamedata.GameData;
import blackjackanalyzer.input.InputReader;
import blackjackanalyzer.input.InputSorter;
import blackjackanalyzer.input.InputValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize reader, validator, sorter, analyzer and writer classes.
        InputReader reader = new InputReader();
        InputValidator validator = new InputValidator();
        InputSorter sorter = new InputSorter();

        // Check the input game-data lines.
        List<String> gameDataString = reader.readGameDataFromFile("game_data_0.txt");
        List<String> checkedGameDataString = validator.checkAllGameDataLines(gameDataString);

        // Convert lines of string to GameDataObjects
        List<GameData> gameDataList = sorter.convertAllDataLinesToGameDataObjects(checkedGameDataString);

        // Sort gameDataList by sessionID and timestamp
        List<GameData> sortedGameDataList = sorter.sortGameDataObjectsBySessionAndTime(gameDataList);
        System.out.println(sortedGameDataList);
    }
}

