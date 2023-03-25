package blackjackanalyzer.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    List<String> listOfLines = new ArrayList<>();

    /**
     *
     * @param filename
     * @return List<String>
     */
    public List<String> readGameDataFromFile(String filename) {

        // Get file path
        Path path = Paths.get(filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

            // Line of text from file in string format
            String line;

            // While the line does not equal null, add the string of text to a list
            while ((line = reader.readLine()) != null) {
                listOfLines.add(line);
            }
        }

        // Catch block to handle the exceptions
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return listOfLines;
    }
}
