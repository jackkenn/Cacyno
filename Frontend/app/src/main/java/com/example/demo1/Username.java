package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;


public class Username extends AppCompatActivity {
    private TextView username;
    private ImageButton apply;
    private User user;
    private IUser callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_username_screen);
        user = new User();
        callback = new IUser() {
            @Override
            public int onSuccess() {
                return 0;
            }

            @Override
            public int onError() {
                return -1;
            }
        };
        user.getUser(this, callback, FirebaseAuth.getInstance().getCurrentUser().getUid());
        username = (TextView) findViewById(R.id.username);
        apply = (ImageButton) findViewById(R.id.apply_but);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = username.getText().toString();
                if(input.isEmpty()){
                    username.setError("Please provide username");
                    username.requestFocus();
                }
                else if(input.equals("username")){
                    username.setError("Please provide username");
                    username.requestFocus();
                }
                else{
                    user.setUsername(input);
                    user.updateUser(Username.this, callback);
                    Intent I = new Intent(Username.this, UserHome.class);
                    startActivity(I);
                }
            }
        });
    }
}
