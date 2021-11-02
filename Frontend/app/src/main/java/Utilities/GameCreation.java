package Utilities;

import Models.User;
import android.content.Context;
import android.widget.TextView;

public class GameCreation {
    GameChecker  gameChecker;

    public GameCreation(GameChecker checker){
        gameChecker = checker;
    }
    public boolean createGame(String lobbyname, String moneyAmount, TextView name, TextView money, Context con, User user){
        return gameChecker.checkLobbyName(lobbyname, name) && gameChecker.checkMoneyAmount(moneyAmount, money, user);
    }
}
