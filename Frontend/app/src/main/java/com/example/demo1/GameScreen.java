package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class GameScreen extends AppCompatActivity {
    private User user;
    private TextView ingame_money;
    private TextView username;
    private ImageButton backout;
    private ImageButton chat;
    private ConstraintLayout gameScreen;
    private WebSocketClient mWebSocketClient;
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

            //buttons
            ImageButton x = findViewById(R.id.x_out_chat);
            ImageButton send = findViewById(R.id.send_message);
            TextView message = findViewById(R.id.message_type);

            //exit out
            x.setOnClickListener(v1 -> {
                gameScreen.removeView(findViewById(R.id.chat_view_remove));
                gameScreen.invalidate();
            });

        });
    }

    private void connectWebSocket() throws URISyntaxException {
        URI url;
        /*
         * To test the clientside without the backend, simply connect to an echo server such as:
         *  "ws://echo.websocket.org"
         */
        url = new URI("ws://10.0.2.2:8080/example"); // 10.0.2.2 = localhost
        // uri = new URI("ws://echo.websocket.org");

        mWebSocketClient = new WebSocketClient(url) {


            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("connected");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the previous messages
                System.out.println(msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }



}
