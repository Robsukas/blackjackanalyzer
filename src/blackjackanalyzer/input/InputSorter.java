package blackjackanalyzer.input;

import blackjackanalyzer.action.Action;
import blackjackanalyzer.action.ActionBuilder;
import blackjackanalyzer.gamedata.GameData;
import blackjackanalyzer.gamedata.GameDataBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InputSorter {


    /**
     * Convert all the given data lines to GameData objects and add them to a list, then return the list.
     * @param dataLines
     * @return List<GameData>
     */
    public List<GameData> convertAllDataLinesToGameDataObjects(List<String> dataLines) {
        List<GameData> gameDataObjectList = new ArrayList<>();

        // Loop over the given lines of data, convert them to corresponding GameData objects and add to a list.
        for (String line : dataLines) {
            List<String> splitLine = Arrays.asList(line.split(","));
            List<String> actionAsList = Arrays.asList(splitLine.get(3).split(" "));

            // Initialize the action for this current line of data as an Action object.
            Action action = new ActionBuilder()
                    .setPerson(actionAsList.get(0))
                    .setAction(actionAsList.get(1))
                    .createAction();

            // Initialize the GameData object for this current line.
            GameData gameData = new GameDataBuilder()
                    .setTimestamp(Integer.valueOf(splitLine.get(0)))
                    .setGameID(Integer.valueOf(splitLine.get(1)))
                    .setPlayerID(Integer.valueOf(splitLine.get(2)))
                    .setAction(action)
                    .setDealerHand(Arrays.asList(splitLine.get(4).split("-")))
                    .setPlayerHand(Arrays.asList(splitLine.get(5).split("-")))
                    .createGameData();

            // Add the GameData object to a collective list.
            gameDataObjectList.add(gameData);
        }
        return gameDataObjectList;
    }

    /**
     * Iterate through a list of GameData objects and sort it by sessionID and timestamp.
     * @param gameDataObjectList
     * @return List<GameData>
     */
    public List<GameData> sortGameDataObjectsBySessionAndTime(List<GameData> gameDataObjectList) {
        return gameDataObjectList.stream()
                .sorted(Comparator.comparing(GameData::getGameID)
                        .thenComparing(GameData::getTimestamp))
                .toList();
    }
}
