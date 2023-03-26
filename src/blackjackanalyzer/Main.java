package blackjackanalyzer;

import blackjackanalyzer.analyzer.LogicAnalyzer;
import blackjackanalyzer.gamedata.GameData;
import blackjackanalyzer.input.InputReader;
import blackjackanalyzer.input.InputSorter;
import blackjackanalyzer.input.InputValidator;
import blackjackanalyzer.output.OutputWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {


    public static void main(String[] args) {
        Integer faultyGameID = null;
        List<GameData> outputResultList = new ArrayList<>();

        // Initialize reader, validator, sorter, analyzer and writer classes.
        InputReader reader = new InputReader();
        InputValidator validator = new InputValidator();
        InputSorter sorter = new InputSorter();

        // Initialize the logic analyzer
        LogicAnalyzer analyzer = new LogicAnalyzer();

        // Initialize the output writer
        OutputWriter writer = new OutputWriter();


        // Check the input game-data lines (change the filename string in the following line to whatever game data .txt file needs checking).
        List<String> gameDataString = reader.readGameDataFromFile("game_data_2.txt");
        List<String> checkedGameDataString = validator.checkAllGameDataLines(gameDataString);

        // Convert lines of string to GameDataObjects
        List<GameData> gameDataList = sorter.convertAllDataLinesToGameDataObjects(checkedGameDataString);

        // Sort gameDataList by sessionID and timestamp
        List<GameData> sortedGameDataList = sorter.sortGameDataObjectsBySessionAndTime(gameDataList);

        // Iterate through the list and find all the faulty lines
        for (GameData gamedata : sortedGameDataList) {
            if (Objects.equals(faultyGameID, gamedata.getGameID())) {
                continue;
            }
            else if (!(analyzer.checkDealerHandFaceDownCards(gamedata)
                    && analyzer.checkBothHandsForIllegalCards(gamedata)
                    && analyzer.checkForActionCompatabilityWithPrevious(gamedata)
                    && analyzer.checkIfBustAndGameStillContinues(gamedata))) {
                // Set the faulty gameID, so the program doesn't look for new faulty lines in that game session anymore.
                faultyGameID = gamedata.getGameID();

                // Empty previous action list, so the program works as intended for the next game session.
                analyzer.emptyActionList();

                // Add this line of data to a list that will be passed on to the OutputWriter class.
                outputResultList.add(gamedata);
            }
        }
        // Take outputResultList and convert it to a list of strings, then write each string to a new output file.
        // Make sure to clear out output text file before writing there again.
        writer.writeToOutput(writer.convertGameDataObjectsToString(outputResultList), "game_data_output.txt");
    }
}

