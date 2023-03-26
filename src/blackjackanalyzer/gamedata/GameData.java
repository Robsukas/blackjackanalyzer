package blackjackanalyzer.gamedata;

import blackjackanalyzer.action.Action;

import java.util.List;

public class GameData {
    private Integer timestamp;
    private Integer gameID;
    private Integer playerID;
    private Action action;
    private List<String> dealerHand;
    private List<String> playerHand;

    public GameData(Integer timestamp, Integer gameID, Integer playerID, Action action, List<String> dealerHand, List<String> playerHand) {
        this.timestamp = timestamp;
        this.gameID = gameID;
        this.playerID = playerID;
        this.action = action;
        this.dealerHand = dealerHand;
        this.playerHand = playerHand;
    }


    public Integer getTimestamp() {
        return timestamp;
    }

    public Integer getGameID() {
        return gameID;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public Action getAction() {
        return action;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public List<String> getPlayerHand() {
        return playerHand;
    }

    /**
     * Check if the dealer's hand contains a face down card.
     * @return boolean
     */
    public boolean dealerHandContainsFaceDownCard() {
        return getDealerHand().contains("?");
    }

    /**
     * Calculate the total value of all the cards in the player's hand.
     * @return int
     */
    public int getPlayerHandTotal() {
        int total = 0;
        List<String> faceCards = List.of("J", "Q", "K", "A");
        for (String card : playerHand) {
            String value = card.substring(0, 1).toUpperCase();
            if (faceCards.contains(value)) {
                if (value.equals("A")) {
                    total += 11;
                } else {
                    total += 10;
                }
            } else {
                total += Integer.parseInt(value);
            }
        }
        return total;
    }

    /**
     * Calculate the total value of all the cards in the dealer's hand.
     * @return int
     */
    public Integer getDealerHandTotal() {
        // Just to not get an error from having a face down card.
        if (dealerHandContainsFaceDownCard()) {
            return 0;
        }
        int total = 0;
        List<String> faceCards = List.of("J", "Q", "K", "A");
        for (String card : dealerHand) {
            String value = card.substring(0, 1).toUpperCase();
            if (faceCards.contains(value)) {
                if (value.equals("A")) {
                    total += 11;
                } else {
                    total += 10;
                }
            } else {
                total += Integer.parseInt(value);
            }
        }
        return total;
    }

    /**
     * Convert dealer hand list to string.
     * @param dealerHandList
     * @return String
     */
    private String dealerHandToString(List<String> dealerHandList) {
        // Edge case when hand only has one element.
        if (dealerHandList.size() == 1) {
            return dealerHandList.get(0);
        }

        // All normal cases.
        StringBuilder dealerHand = new StringBuilder();
        for (String card : getDealerHand()) {
            dealerHand.append(card);
            dealerHand.append("-");

        }
        dealerHand.deleteCharAt(dealerHand.length() - 1);
        return dealerHand.toString();
    }

    /**
     * Convert player hand list to string.
     * @param playerHandList
     * @return String
     */
    private String playerHandToString(List<String> playerHandList) {
        // Edge case when hand only has one element.
        if (playerHandList.size() == 1) {
            return playerHandList.get(0);
        }
        // All normal cases.
        StringBuilder playerHand = new StringBuilder();
        for (String card : getPlayerHand()) {
            playerHand.append(card);
            playerHand.append("-");
        }
        playerHand.deleteCharAt(playerHand.length() - 1);
        return playerHand.toString();
    }

    @Override
    public String toString() {
        return getTimestamp().toString() + "," + getGameID().toString() + "," + getPlayerID().toString()
                + "," + getAction().toString() + ","
                + dealerHandToString(getDealerHand()) + "," + playerHandToString(getPlayerHand());
    }
}
