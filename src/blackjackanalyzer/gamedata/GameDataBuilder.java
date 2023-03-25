package blackjackanalyzer.gamedata;

import blackjackanalyzer.action.Action;

import java.util.List;

public class GameDataBuilder {
    private Integer timestamp;
    private Integer gameID;
    private Integer playerID;
    private Action action;
    private List<String> dealerHand;
    private List<String> playerHand;

    public GameDataBuilder setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public GameDataBuilder setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
    }

    public GameDataBuilder setPlayerID(Integer playerID) {
        this.playerID = playerID;
        return this;
    }

    public GameDataBuilder setAction(Action action) {
        this.action = action;
        return this;
    }

    public GameDataBuilder setDealerHand(List<String> dealerHand) {
        this.dealerHand = dealerHand;
        return this;
    }

    public GameDataBuilder setPlayerHand(List<String> playerHand) {
        this.playerHand = playerHand;
        return this;
    }

    public GameData createGameData() {
        return new GameData(timestamp, gameID, playerID, action, dealerHand, playerHand);
    }
}