package interfaces;

public interface ITextViews {

    void MyMoney(int money);

    void MyCard1(int card);

    void MyCard2(int card);

    void MyBet(int bet);

    void Player1Username(String username);

    void Player1Money(String money);

    void Player1Bet(int bet);

    void Player2Username(String username);

    void Player2Money(String money);

    void Player2Bet(int bet);

    void Player3Username(String username);

    void Player3Money(String money);

    void Player3Bet(int bet);

    void Player4Username(String username);

    void Player4Money(String money);

    void Player4Bet(int bet);

    void Player5Username(String username);

    void Player5Money(String money);

    void Player5Bet(int bet);

    void TableCard1(int card);

    void TableCard2(int card);

    void TableCard3(int card);

    void TableCard4(int card);

    void TableCard5(int card);

    void ToastComments(String msg);

    void pot(int pot);

    void raiseAmount(int highest_bet);

    void setHighestBet(int highest_bet);

    void setBet(int bet);
}
