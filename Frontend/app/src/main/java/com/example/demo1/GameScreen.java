package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;

import java.util.Objects;
import java.util.Random;

public class GameScreen extends AppCompatActivity {
    private User user;
    private TextView ingame_money;
    private TextView username;
    private ImageButton backout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        ingame_money = findViewById(R.id.money_ingame);
        username = findViewById(R.id.username_ingame);
        backout = findViewById(R.id.back_from_game);

        user = new User();
        user.getUser(GameScreen.this, new IUser() {
            @Override
            public int onSuccess() {
                ingame_money.append(user.getCurrent_game_money()+"");
                username.append( (user.getDisplayName()) ? user.getUsername() : "user"+ new Random().nextInt(10000)+1);
                return 0;
            }

            @Override
            public int onError() {
                return -1;
            }
        }, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), true);


        backout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.resetUser();
                user.updateUser(GameScreen.this, new IUser() {
                    @Override
                    public int onSuccess() {
                        startActivity(new Intent(GameScreen.this, UserHome.class));
                        return 0;
                    }

                    @Override
                    public int onError() {
                        return 0;
                    }
                }, true);
            }
        });
    }
}
