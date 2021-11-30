package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen extends AppCompatActivity {
    private User user;
    private TextView ingame_money;
    private TextView username;
    private ImageButton backout;
    private ImageButton chat;
    private ConstraintLayout gameScreen;
    private WebSocketClient mWebSocketClient;
    private ImageButton raise;
    private ImageButton fold;
    private ImageButton check;
    private TextView pot;
    private Slider slider;
    private TextView sliderAmount;
    private LinearLayout chatlayout;
    int rand = 0;
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ingame_money = findViewById(R.id.money_ingame);
        username = findViewById(R.id.username_ingame);
        backout = findViewById(R.id.back_from_game);
        chat = findViewById(R.id.chat_but);
        raise = findViewById(R.id.raise);
        fold = findViewById(R.id.fold);
        check = findViewById(R.id.check);
        pot = findViewById(R.id.pot);
        slider = findViewById(R.id.slider);
        sliderAmount = findViewById(R.id.slider_amount);

        gameScreen = findViewById(R.id.ActualGame);

        getLayoutInflater().inflate(R.layout.chat_view, gameScreen);
        //buttons
        ImageButton x = findViewById(R.id.x_out_chat);
        ImageButton send = findViewById(R.id.send_message);
        TextView message = findViewById(R.id.message_type);
        chatlayout = findViewById(R.id.linearchat);

        bringToFront();

        user = new User();
        user.getUser(GameScreen.this, new IUser() {
            @Override
            public int onSuccess() throws JSONException, URISyntaxException {
                ingame_money.append(user.getCurrent_game_money()+"");
                rand = new Random().nextInt(10000)+1;
                username.append((user.getDisplayName()) ? user.getUsername() : "user" + rand);
                connectWebSocket();
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
        AtomicInteger messages= new AtomicInteger(1);
        chat.setOnClickListener(v -> {
            findViewById(R.id.chat_view_remove).bringToFront();
            x.setOnClickListener(v1 -> bringToFront());

            send.setOnClickListener(v2 -> {
                View newmessage = getLayoutInflater().inflate(R.layout.chat_row, chatlayout);
                newmessage.setId(messages.get());

                TableRow row = findViewById(R.id.newChat);
                row.setId(messages.get() * 1000);

                TextView text = findViewById(R.id.message);
                text.setId(messages.get() *10);

                TextView user_message = findViewById(R.id.Sentby);
                user_message.setId(messages.get() *100);

                row.setGravity(Gravity.RIGHT);
                user_message.append((user.getDisplayName()) ? user.getUsername() : "user" + rand);
                text.append(message.getText().toString());
                messages.getAndIncrement();

                //mWebSocketClient.send(message.getText().toString());
            });
        });
    }

    private void bringToFront(){
        findViewById(R.id.game_background).bringToFront();

        chat.bringToFront();
        backout.bringToFront();
        check.bringToFront();
        fold.bringToFront();
        raise.bringToFront();
        username.bringToFront();
        ingame_money.bringToFront();
        pot.bringToFront();

        findViewById(R.id.user_tag).bringToFront();
        findViewById(R.id.user_tag).bringToFront();
        findViewById(R.id.money_tag).bringToFront();
        findViewById(R.id.pot_tag).bringToFront();

        findViewById(R.id.your_money_sign).bringToFront();
        findViewById(R.id.slider_moneysign).bringToFront();

        findViewById(R.id.player1_card1).bringToFront();
        findViewById(R.id.player1_card2).bringToFront();
        findViewById(R.id.player1_line).bringToFront();
        findViewById(R.id.player1_money).bringToFront();
        findViewById(R.id.player1_username).bringToFront();

        findViewById(R.id.player2_card1).bringToFront();
        findViewById(R.id.player2_card2).bringToFront();
        findViewById(R.id.player2_line).bringToFront();
        findViewById(R.id.player2_money).bringToFront();
        findViewById(R.id.player2_username).bringToFront();

        findViewById(R.id.player3_card1).bringToFront();
        findViewById(R.id.player3_card2).bringToFront();
        findViewById(R.id.player3_line).bringToFront();
        findViewById(R.id.player3_money).bringToFront();
        findViewById(R.id.player3_username).bringToFront();

        findViewById(R.id.player4_card1).bringToFront();
        findViewById(R.id.player4_card2).bringToFront();
        findViewById(R.id.player4_line).bringToFront();
        findViewById(R.id.player4_money).bringToFront();
        findViewById(R.id.player4_username).bringToFront();

        findViewById(R.id.player5_card1).bringToFront();
        findViewById(R.id.player5_card2).bringToFront();
        findViewById(R.id.player5_line).bringToFront();
        findViewById(R.id.player5_money).bringToFront();
        findViewById(R.id.player5_username).bringToFront();

        findViewById(R.id.yourCard_1).bringToFront();
        findViewById(R.id.yourCard_2).bringToFront();

        findViewById(R.id.middlecard_1).bringToFront();
        findViewById(R.id.middlecard_2).bringToFront();
        findViewById(R.id.middlecard_3).bringToFront();
        findViewById(R.id.middlecard_4).bringToFront();
        findViewById(R.id.middlecard_5).bringToFront();
    }

    private void connectWebSocket() throws URISyntaxException, JSONException {
        URI url;
        /*
         * To test the clientside without the backend, simply connect to an echo server such as:
         *  "ws://echo.websocket.org"
         */
        url = new URI("http://localhost:8080/"+user.getGameId().getString("id")+"/"+user.getId()); // 10.0.2.2 = localhost
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
