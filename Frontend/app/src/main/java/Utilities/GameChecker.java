package Utilities;

import Models.User;
import android.widget.TextView;

public class GameChecker {

    public boolean checkLobbyName(String name, TextView nameView){
        if(name.isEmpty()){
            nameView.setError("please provide lobby name");
            nameView.requestFocus();
            return false;
        }
        else if(name.length() > 12){
            nameView.setError("please stay within max length");
            nameView.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkMoneyAmount(String money, TextView moneyView, User user){
        if(money.isEmpty())
        {
            moneyView.setError("please supply amount");
            moneyView.requestFocus();
            return false;
        }
        else if(money.matches(".*[a-z].*")){
            moneyView.setError("no letters please");
            moneyView.requestFocus();
            return false;
        }
        else if (money.charAt(0) == '0'){
            moneyView.setError("please supply real amount");
            moneyView.requestFocus();
            return false;
        }
        else if(Integer.parseInt(money) > user.getMoney()){
            moneyView.setError(user.getUsername() + " too broke");
            moneyView.requestFocus();
            return false;
        }
        return true;
    }
}
