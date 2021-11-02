package Utilities;

import android.content.Context;
import android.widget.EditText;

public class AccountCreation {
    AccountChecker accountChecker = new AccountChecker();


    public boolean createAccount(String username, String password, EditText email, EditText passwd, Context context) {
        if (accountChecker.checkEmail(username, email) && accountChecker.checkPassword(password, passwd)) {
            return true;
        } else {
            return false;
        }
    }
}
