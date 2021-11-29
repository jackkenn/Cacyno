package Utilities;

import Models.User;
import android.content.Context;
import android.widget.TextView;

public class GameCreation {
    GameChecker  gameChecker;

    /**
     * Constructor for a game to add to db
     * @param checker the checker needed to make sure game is in correct format
     */
    public GameCreation(GameChecker checker){
        gameChecker = checker;
    }

    /**
     * this method will create a game but make sure the parameters are correct
     * @param lobbyname the name of the lobby
     * @param moneyAmount the amount
     * @param name
     * @param money
     * @param con
     * @param user
     * @return
     */
    public boolean createGame(String lobbyname, String moneyAmount, TextView name, TextView money, Context con, User user){
        return gameChecker.checkLobbyName(lobbyname, name) && gameChecker.checkMoneyAmount(moneyAmount, money, user);
    }
}
