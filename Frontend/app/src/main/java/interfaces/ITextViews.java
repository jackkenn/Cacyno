package interfaces;

import java.util.ArrayList;

public interface ITextViews {

    void MyMoney(int money);

    void MyCard1(int card);

    void MyCard2(int card);

    void Player1Username(String username);

    void Player1Money(String money);

    void Player2Username(String username);

    void Player2Money(String money);

    void Player3Username(String username);

    void Player3Money(String money);

    void Player4Username(String username);

    void Player4Money(String money);

    void Player5Username(String username);

    void Player5Money(String money);

    void TableCard1(int card);

    void TableCard2(int card);

    void TableCard3(int card);

    void TableCard4(int card);

    void TableCard5(int card);

    void ToastComments(String msg);

    void pot(int pot);

    void setGreen(int player);

    void setFolded(ArrayList<Integer> indicesOfFolded);
}
