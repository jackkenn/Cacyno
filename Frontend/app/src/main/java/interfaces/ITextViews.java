package interfaces;

import java.util.ArrayList;

public interface ITextViews {

    void MyMoney(int money);

    void MyCard1(int card);

    void MyCard2(int card);

    void MyBet(int bet);

    void Player1Username(String username, boolean removeDot);

    void Player1Money(String money);

    void Player2Username(String username, boolean removeDot);
    void Player1Bet(int bet);


    void Player2Money(String money);

    void Player3Username(String username, boolean removeDot);
    void Player2Bet(int bet);


    void Player3Money(String money);

    void Player4Username(String username, boolean removeDot);
    void Player3Bet(int bet);


    void Player4Money(String money);

    void Player5Username(String username, boolean removeDot);
    void Player4Bet(int bet);


    void Player5Money(String money);

    void Player5Bet(int bet);

    void TableCard1(int card);

    void TableCard2(int card);

    void TableCard3(int card);

    void TableCard4(int card);

    void TableCard5(int card);

    void ToastComments(String msg);

    void pot(int pot);

    void setSliderTo(int to);

    void setGreen(int player);

    void setFolded(ArrayList<Integer> indicesOfFolded);

    void setWhite(ArrayList<Integer> indicesOfCurrentPlayers);


    void raiseAmount(int highest_bet);

    void setHighestBet(int highest_bet);

    void setBet(int bet);

    void setWinner(String username);
}
