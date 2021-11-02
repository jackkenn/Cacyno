package Utilities;

import android.widget.EditText;

public class AccountChecker {
    public boolean checkEmail(String email, EditText emailId) {
        if(email.isEmpty())
        {
            emailId.setError("Provide your Email first!");
            emailId.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkPassword(String password, EditText passwd) {
        if(password.isEmpty())
        {
            passwd.setError("Set your password");
            passwd.requestFocus();
            return false;
        }
        return true;
    }
}
