package blackjackanalyzer.output;

import blackjackanalyzer.gamedata.GameData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OutputWriter {

    public List<String> convertGameDataObjectsToString(List<GameData> listToConvert) {
        List<String> resultList = new ArrayList<>();
        for (GameData line : listToConvert) {
            resultList.add(line.toString());
        }
        return resultList;
    }

    /**
     * Take in a list of data to write in to the output file.
     * @param outputList
     * @param filename
     */
    public void writeToOutput(List<String> outputList, String filename) {

        // Get file path
        Path path = Paths.get(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            // Write lines to file.
            for (String line : outputList) {
                writer.write(line);
                writer.newLine();
            }
        }

        // Catch block to handle the exceptions
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
