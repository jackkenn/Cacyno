package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;

import java.util.Objects;
import java.util.Random;

public class GameScreen extends AppCompatActivity {
    private User user;
    private TextView ingame_money;
    private TextView username;
    private ImageButton backout;
    private ImageButton chat;
    private ConstraintLayout gameScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ingame_money = findViewById(R.id.money_ingame);
        username = findViewById(R.id.username_ingame);
        backout = findViewById(R.id.back_from_game);
        chat = findViewById(R.id.chat_but);
        gameScreen = findViewById(R.id.ActualGame);

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


        backout.setOnClickListener(v -> {
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
        });

        chat.setOnClickListener(v -> {
            View chatview = getLayoutInflater().inflate(R.layout.chat_view, gameScreen);
            chatview.bringToFront();
            ImageButton x = findViewById(R.id.x_out_chat);

            x.setOnClickListener(v1 -> {
                gameScreen.removeView(findViewById(R.id.chat_view_remove));
                gameScreen.invalidate();
            });

        });
    }
}
