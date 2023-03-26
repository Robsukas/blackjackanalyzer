package blackjackanalyzer.analyzer;

import blackjackanalyzer.action.Action;
import blackjackanalyzer.gamedata.GameData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicAnalyzer {
    List<Action> actionList = new ArrayList<>();
    int previousID = 0;

    /**
     * Preliminary check that bypasses most other checks if true, because there are no cards in play to validate.
     * @param gameData
     * @return boolean
     */
    public boolean areBothHandsEmpty(GameData gameData) {
        // A check for an edge case, when player has left and no cards are shown anymore.
        return gameData.getDealerHand().isEmpty() && gameData.getPlayerHand().isEmpty();
    }

    public void emptyActionList() {
        actionList.clear();
    }

    /**
     * Check if both hands are correct, return corresponding boolean value.
     * @param gameData
     * @return boolean
     */
    public boolean checkDealerHandFaceDownCards(GameData gameData) {
        // A check for an edge case, when player has left and no cards are shown anymore.
        if (areBothHandsEmpty(gameData)) {
            return true;
        }

        // Check that dealer has only one face down card.
        int countOfFaceDownCards = 0;
        for (String card : gameData.getDealerHand()) {
            if (card.equals("?")) {
                countOfFaceDownCards++;
            }
        }
        return countOfFaceDownCards <= 1;
    }

    /**
     * Check both the dealer's and the player's hands to see if they have any incorrect cards.
     * @param gameData
     * @return boolean
     */
    public boolean checkBothHandsForIllegalCards(GameData gameData) {
        // A check for an edge case, when player has left and no cards are shown anymore.
        if (areBothHandsEmpty(gameData)) {
            return true;
        }

        List<String> combinedHandList = new ArrayList<>();
        List<String> acceptedRanks = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        List<String> acceptedSuits = List.of("C", "S", "D", "H");

        // Add both cards in the uppercase format to a combined list
        for (String card : gameData.getDealerHand()) {
            combinedHandList.add(card.toUpperCase());
        }
        for (String card : gameData.getPlayerHand()) {
            combinedHandList.add(card.toUpperCase());
        }

        // Check for duplicate cards.
        Set<String> combinedHandSet = new HashSet<String>(combinedHandList);
        if (combinedHandSet.size() != combinedHandList.size()) {
            return false;
        }

        boolean check = true;
        for (String card : combinedHandList) {
            if (check) {
                if (card.length() == 2) {
                    check = acceptedRanks.contains(card.substring(0, 1)) && acceptedSuits.contains(card.substring(1, 2));
                }
                else if (card.length() == 3) {
                    check = acceptedRanks.contains(card.substring(0, 2)) && acceptedSuits.contains(card.substring(2, 3));
                }
                else {
                    check = card.equals("?");
                }
            }
        }
        return check;
    }

    /**
     * Check if the current action is compatible with the previous action.
     * @param gameData
     * @return boolean
     */
    public boolean checkForActionCompatabilityWithPrevious(GameData gameData) {
        List<String> playerLegalActions = List.of("hit", "stand", "lose", "win", "left", "joined");
        List<String> dealerLegalActions = List.of("hit", "show", "redeal");
        Action action = gameData.getAction();
        String currentPerson = action.getPerson();
        String currentAction = action.getAction();

        // Case if there are no previous actions.
        if (actionList.isEmpty()) {
            actionList.add(action);
            return currentPerson.equalsIgnoreCase("P")
                    && currentAction.equalsIgnoreCase("Joined");
        }

        // Add current action to the list and get the one before that, (list.size() - 2).
        actionList.add(action);
        Action lastAction = actionList.get(actionList.size() - 2);
        String previousPerson = lastAction.getPerson();
        String previousAction = lastAction.getAction();

        // Player cases
        if (currentPerson.equalsIgnoreCase("P")) {

            // Check if action is legal
            if (!playerLegalActions.contains(currentAction.toLowerCase())) {
                return false;
            }

            // Case: Action is Left
            if (currentAction.equalsIgnoreCase("Left")) {
                return previousAction.equalsIgnoreCase("Win")
                        || previousAction.equalsIgnoreCase("Lose");
            }

            // Case: Action is Win or Lose
            else if (currentAction.equalsIgnoreCase("Win")
                    || currentAction.equalsIgnoreCase("Lose")) {
                return previousAction.equalsIgnoreCase("Hit")
                        || previousAction.equalsIgnoreCase("Show");
            }

            // Case: Action is Hit
            else if (currentAction.equalsIgnoreCase("Hit")) {
                return previousAction.equalsIgnoreCase("Joined")
                        || previousAction.equalsIgnoreCase("Redeal")
                        || (previousAction.equalsIgnoreCase("Hit")
                        && previousPerson.equalsIgnoreCase("P"));
            }

            // Case: Action is Stand
            else if (currentAction.equalsIgnoreCase("Stand")) {
                return (previousAction.equalsIgnoreCase("Hit")
                        && previousPerson.equalsIgnoreCase("P"))
                        || previousAction.equalsIgnoreCase("Redeal")
                        || previousAction.equalsIgnoreCase("Joined");
            }
            else {
                return false;
            }
        }
        // Dealer cases
        else if (currentPerson.equalsIgnoreCase("D")) {

            // Check if action is legal
            if (!dealerLegalActions.contains(currentAction.toLowerCase())) {
                return false;
            }

            // Case: Redeal
            else if (currentAction.equalsIgnoreCase("Redeal")) {
                return previousAction.equalsIgnoreCase("Win")
                        || previousAction.equalsIgnoreCase("Lose");
            }

            // Case: Show
            else if (currentAction.equalsIgnoreCase("Show")) {
                return previousAction.equalsIgnoreCase("Stand");
            }

            // Case: Hit
            else if (currentAction.equalsIgnoreCase("Hit")) {
                return previousAction.equalsIgnoreCase("Show")
                        || (previousAction.equalsIgnoreCase("Hit")
                        && previousPerson.equalsIgnoreCase("D"));
            }
            else {
                return false;
            }
        }

        // Always return false, when person isn't dealer or player.
        else {
            return false;
        }

    }

    /**
     * Check if either the player or the dealer is bust, but the game still continues.
     * If the game continues while either of the players is bust, return false, else return true.
     *
     * @param gameData
     * @return boolean
     */
    public boolean checkIfBustAndGameStillContinues(GameData gameData) {
        // A check for an edge case, when player has left and no cards are shown anymore.
        if (areBothHandsEmpty(gameData)) {
            return true;
        }

        Action action = gameData.getAction();
        int dealerTotal = gameData.getDealerHandTotal();
        int playerTotal = gameData.getPlayerHandTotal();

        // A check for an edge case, when dealer's hand still contains a face down card
        if (gameData.getDealerHand().contains("?")) {
            if (playerTotal > 21) {
                return action.getAction().equalsIgnoreCase("Lose");
            } else {
                return true;
            }
        }

        // Case: Player Win
        else if (dealerTotal > 21 || (playerTotal >= dealerTotal && dealerTotal >= 17)) {
            return action.getAction().equalsIgnoreCase("Win");
        }

        // Case: Player Lose
        else if (dealerTotal >= 17) {
            return action.getAction().equalsIgnoreCase("Lose");
        }
        // Any other case, return true
        else {
            return true;
        }
    }
}
