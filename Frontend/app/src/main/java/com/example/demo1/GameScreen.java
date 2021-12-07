package com.example.demo1;

import Models.GameInstance;
import Models.User;
import Utilities.ImageIdHashMap;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.ITextViews;
import interfaces.IUser;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class GameScreen extends AppCompatActivity implements ITextViews {
    private User user;
    private TextView ingame_money;
    private TextView username;

    private ImageButton backout;
    private ImageButton chat;
    private ImageButton raise;
    private ImageButton fold;
    private ImageButton check;
    private Slider slider;

    private ConstraintLayout gameScreen;
    private WebSocketClient mWebSocketClient;

    private TextView pot;
    private TextView sliderAmount;

    private LinearLayout chatlayout;
    private ImageView yourCard1;
    private ImageView yourCard2;
    ImageIdHashMap imageIdsHashMap = new ImageIdHashMap();
    ScrollView scroll;
    HashMap<Integer, Integer> imageIds;
    int highest_bet;
    int bet;

    private GameInstance game;

    int randForUsername = 0;

    @SuppressLint("RtlHardcoded")
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageIds = imageIdsHashMap.getImageIds();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //all interactions that the user can do
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
        yourCard1 = findViewById(R.id.yourCard_1);
        yourCard2 = findViewById(R.id.yourCard_2);


        //the constraint layout to add chat view
        gameScreen = findViewById(R.id.ActualGame);

        //add chat view
        View chatplz = getLayoutInflater().inflate(R.layout.chat_view, gameScreen);

        //interactions for chat and layouts
        ImageButton x = findViewById(R.id.x_out_chat);
        ImageButton send = findViewById(R.id.send_message);
        TextView message = findViewById(R.id.message_type);
        chatlayout = findViewById(R.id.linearchat);
        scroll = chatplz.findViewById(R.id.chat_scroll);
        slider.setStepSize(1);

        bringToFront();
        setIdle();

        user = new User();
        user.getUser(GameScreen.this, new IUser() {
            @Override
            public int onSuccess() throws JSONException, URISyntaxException {
                System.out.println("Success Getting User");
                ingame_money.setText("$" + user.getCurrent_game_money());
                randForUsername = new Random().nextInt(10000) + 1;
                username.append((user.getDisplayName()) ? user.getUsername() : "user" + randForUsername);
                slider.setValueFrom(0);
                slider.setValueTo(user.getCurrent_game_money());
                connectWebSocket();
                game = new GameInstance(user, GameScreen.this);
                return 0;
            }

            @Override
            public int onError() {
                System.out.println("Failed Getting User");
                return -1;
            }
        }, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), true);


        backout.setOnClickListener(v -> {
            user.resetUser();
            user.updateUser(GameScreen.this, new IUser() {
                @Override
                public int onSuccess() {
                    mWebSocketClient.close();
                    game.closeWebsocket();
                    startActivity(new Intent(GameScreen.this, UserHome.class));
                    return 0;
                }

                @Override
                public int onError() {
                    return 0;
                }
            }, true);
        });

        raise.setOnClickListener(v -> {
            game.send("Bet: " + sliderAmount.toString());
        });

        check.setOnClickListener(v -> {
            if (bet == highest_bet) {
                game.send("Bet: 0");
            } else {
                game.send("Bet: " + (highest_bet-bet));
            }
        });

        fold.setOnClickListener(v -> {
            game.send("Bet: -1");
        });


        chat.setOnClickListener(v -> {
            findViewById(R.id.chat_view_remove).bringToFront();

            x.setOnClickListener(v1 -> bringToFront());

            send.setOnClickListener(v2 -> {
                //adding new message row
                View newFrommessage = getLayoutInflater().inflate(R.layout.chat_row, chatlayout);
                newFrommessage.setId(View.generateViewId());
                TableRow row = newFrommessage.findViewById(R.id.newChat);
                row.setId(View.generateViewId());

                TextView text = newFrommessage.findViewById(R.id.message);
                text.setId(View.generateViewId());

                TextView user_message = newFrommessage.findViewById(R.id.Sentby);
                user_message.setId(View.generateViewId());
                user_message.append((user.getDisplayName()) ? user.getUsername() : "user" + randForUsername);
                text.append(message.getText().toString());

                row.setGravity(Gravity.END);
                mWebSocketClient.send(message.getText().toString());
                message.setText("");
                //auto-scroll
                scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
            });

        });
        slider.setOnChangeListener((slider, value) -> sliderAmount.setText("$" + (int) value));
    }


    /**
     * this method brings everything except the chatview to the front
     */
    private void bringToFront() {

        findViewById(R.id.game_background).bringToFront();

        chat.bringToFront();
        backout.bringToFront();
        check.bringToFront();
        fold.bringToFront();
        raise.bringToFront();
        username.bringToFront();
        ingame_money.bringToFront();
        pot.bringToFront();
        slider.bringToFront();
        sliderAmount.bringToFront();

        findViewById(R.id.user_tag).bringToFront();
        findViewById(R.id.user_tag).bringToFront();
        findViewById(R.id.money_tag).bringToFront();
        findViewById(R.id.pot_tag).bringToFront();
        findViewById(R.id.your_greendot).bringToFront();

        findViewById(R.id.slider_amount).bringToFront();

        findViewById(R.id.player1_card1).bringToFront();
        findViewById(R.id.player1_card2).bringToFront();
        findViewById(R.id.player1_line).bringToFront();
        findViewById(R.id.player1_money).bringToFront();
        findViewById(R.id.player1_username).bringToFront();
        findViewById(R.id.player1_greendot).bringToFront();

        findViewById(R.id.player2_card1).bringToFront();
        findViewById(R.id.player2_card2).bringToFront();
        findViewById(R.id.player2_line).bringToFront();
        findViewById(R.id.player2_money).bringToFront();
        findViewById(R.id.player2_username).bringToFront();
        findViewById(R.id.player2_greendot).bringToFront();

        findViewById(R.id.player3_card1).bringToFront();
        findViewById(R.id.player3_card2).bringToFront();
        findViewById(R.id.player3_line).bringToFront();
        findViewById(R.id.player3_money).bringToFront();
        findViewById(R.id.player3_username).bringToFront();
        findViewById(R.id.player3_greendot).bringToFront();

        findViewById(R.id.player4_card1).bringToFront();
        findViewById(R.id.player4_card2).bringToFront();
        findViewById(R.id.player4_line).bringToFront();
        findViewById(R.id.player4_money).bringToFront();
        findViewById(R.id.player4_username).bringToFront();
        findViewById(R.id.player4_greendot).bringToFront();

        findViewById(R.id.player5_card1).bringToFront();
        findViewById(R.id.player5_card2).bringToFront();
        findViewById(R.id.player5_line).bringToFront();
        findViewById(R.id.player5_money).bringToFront();
        findViewById(R.id.player5_username).bringToFront();
        findViewById(R.id.player5_greendot).bringToFront();

        findViewById(R.id.yourCard_1).bringToFront();
        findViewById(R.id.yourCard_2).bringToFront();

        findViewById(R.id.middlecard_1).bringToFront();
        findViewById(R.id.middlecard_2).bringToFront();
        findViewById(R.id.middlecard_3).bringToFront();
        findViewById(R.id.middlecard_4).bringToFront();
        findViewById(R.id.middlecard_5).bringToFront();
    }

    /**
     * connecting to chat websocket
     *
     * @throws URISyntaxException
     * @throws JSONException
     */
    private void connectWebSocket() throws URISyntaxException, JSONException {
        URI uri;

        //need to change to remote
        uri = new URI("ws://coms-309-046.cs.iastate.edu:8080/chat/" + user.getGameId().getString("id") + "/" + user.getUsername());
        //uri = new URI("ws://192.168.1.2:8080/chat/1/2");

        mWebSocketClient = new WebSocketClient(uri) {


            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("connected");
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the previous messages
                String username = msg.split(":")[0];
                String messsage = msg.split(": ")[1];

                runOnUiThread(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        //this if statement is to ensure the user doesn't receive their own message back
                        if (!Objects.equals(username, user.getUsername())) {

                            View newmessage = getLayoutInflater().inflate(R.layout.chat_row, chatlayout);
                            newmessage.setId(View.generateViewId());

                            TableRow row = newmessage.findViewById(R.id.newChat);
                            row.setId(View.generateViewId());

                            TextView text = newmessage.findViewById(R.id.message);
                            text.setId(View.generateViewId());

                            TextView user_message = newmessage.findViewById(R.id.Sentby);
                            user_message.setId(View.generateViewId());
                            row.setGravity(Gravity.START);

                            //this if statement is when users are connecting and disconnecting. So it does not show "User" for username under text
                            if (!username.equals("User"))
                                user_message.append(username);
                            text.append(messsage);
                            //auto scroll
                            scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
                        }
                        System.out.println(msg);

                    }
                });

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


    @Override
    public void MyMoney(int money) {
        ingame_money.setText("$" + money);
    }

    @Override
    public void MyCard1(int card) {
        ImageView temp = findViewById(R.id.yourCard_1);
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void MyCard2(int card) {
        ImageView temp = findViewById(R.id.yourCard_2);
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void MyBet(int bet) {
        TextView temp = findViewById(R.id.your_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void Player1Username(String username, boolean removeDot) {
        runOnUiThread(() -> {
            findViewById(R.id.player1_greendot).setVisibility(View.VISIBLE);
            if(removeDot)
                findViewById(R.id.player1_greendot).setVisibility(View.INVISIBLE);

            TextView temp = findViewById(R.id.player1_username);
            temp.setText(username);

        });

    }

    @Override
    public void Player1Money(String money) {
        runOnUiThread(() -> {
            System.out.println("money-> "+money);
            TextView temp = findViewById(R.id.player1_money);
            temp.setText(money);
        });
    }

    @Override
    public void Player1Bet(int bet) {
        TextView temp = findViewById(R.id.player1_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void Player2Username(String username, boolean removeDot) {
        runOnUiThread(() -> {
            ((ImageView) findViewById(R.id.player2_greendot)).setVisibility(View.VISIBLE);
            if(removeDot)
                findViewById(R.id.player2_greendot).setVisibility(View.INVISIBLE);

            TextView temp = findViewById(R.id.player2_username);
            temp.setText(username);
        });
    }

    @Override
    public void Player2Money(String money) {
        runOnUiThread(() -> {
            TextView temp = findViewById(R.id.player2_money);
            temp.setText(money);
        });
    }

    @Override
    public void Player2Bet(int bet) {
        TextView temp = findViewById(R.id.player2_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void Player3Username(String username, boolean removeDot) {
        runOnUiThread(() -> {
            ((ImageView) findViewById(R.id.player3_greendot)).setVisibility(View.VISIBLE);
            if(removeDot)
                findViewById(R.id.player3_greendot).setVisibility(View.INVISIBLE);

            TextView temp = findViewById(R.id.player3_username);
            temp.setText(username);
        });
    }

    @Override
    public void Player3Money(String money) {
        runOnUiThread(() -> {
            TextView temp = findViewById(R.id.player3_money);
            temp.setText(money);
        });
    }

    @Override
    public void Player3Bet(int bet) {
        TextView temp = findViewById(R.id.player3_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void Player4Username(String username, boolean removeDot) {
        runOnUiThread(() -> {
            ((ImageView) findViewById(R.id.player4_greendot)).setVisibility(View.VISIBLE);
            if(removeDot)
                findViewById(R.id.player4_greendot).setVisibility(View.INVISIBLE);

            TextView temp = findViewById(R.id.player4_username);
            temp.setText(username);
        });
    }

    @Override
    public void Player4Money(String money) {
        runOnUiThread(() -> {
            TextView temp = findViewById(R.id.player4_money);
            temp.setText(money);
        });
    }

    @Override
    public void Player4Bet(int bet) {
        TextView temp = findViewById(R.id.player4_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void Player5Username(String username, boolean removeDot) {
        runOnUiThread(() -> {
            ((ImageView) findViewById(R.id.player5_greendot)).setVisibility(View.VISIBLE);
            if(removeDot)
                findViewById(R.id.player5_greendot).setVisibility(View.INVISIBLE);

            TextView temp = findViewById(R.id.player5_username);
            temp.setText(username);
        });
    }

    @Override
    public void Player5Money(String money) {
        runOnUiThread(() -> {
            TextView temp = findViewById(R.id.player5_money);
            temp.setText(money);
        });
    }

    @Override
    public void Player5Bet(int bet) {
        TextView temp = findViewById(R.id.player5_bet);
        temp.setText("$" + bet);
    }

    @Override
    public void TableCard1(int card) {
        ImageView temp = findViewById(R.id.middlecard_1);
        if (card == -1) {
            temp.setImageResource(R.drawable.backcard);
        }
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void TableCard2(int card) {
        ImageView temp = findViewById(R.id.middlecard_2);
        if (card == -1) {
            temp.setImageResource(R.drawable.backcard);
        }
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void TableCard3(int card) {
        ImageView temp = findViewById(R.id.middlecard_3);
        if (card == -1) {
            temp.setImageResource(R.drawable.backcard);
        }
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void TableCard4(int card) {
        ImageView temp = findViewById(R.id.middlecard_4);
        if (card == -1) {
            temp.setImageResource(R.drawable.backcard);
        }
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void TableCard5(int card) {
        ImageView temp = findViewById(R.id.middlecard_5);
        if (card == -1) {
            temp.setImageResource(R.drawable.backcard);
        }
        temp.setImageResource(imageIds.get(card));
    }

    @Override
    public void pot(int pot) {
        runOnUiThread(() -> {
            TextView temp = findViewById(R.id.pot);
            temp.setText("$" + pot);
        });
    }

    /**
     * finds the player on screen to set green dot
     * @param player the player will have the green dot
     */
    @Override
    public void setGreen(int player){
        runOnUiThread(() -> {
            switch(player){
                case 0:
                    ((ImageView) findViewById(R.id.your_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                case 1:
                    ((ImageView) findViewById(R.id.player1_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                case 2:
                    ((ImageView) findViewById(R.id.player2_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                case 3:
                    ((ImageView) findViewById(R.id.player3_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                case 4:
                    ((ImageView) findViewById(R.id.player4_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                case 5:
                    ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.green_dot);
                    break;

                default:
                    break;
            }
        });
    }

    /**
     * resets all players dots to white before updating certain ones to folded or the person that will be the green dot
     */
    public void setWhite(ArrayList<Integer> indicesOfCurrentPlayers){
        for(Integer i : indicesOfCurrentPlayers){
            switch(i){
                case 0:
                    ((ImageView) findViewById(R.id.your_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                case 1:
                    ((ImageView) findViewById(R.id.player1_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                case 2:
                    ((ImageView) findViewById(R.id.player2_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                case 3:
                    ((ImageView) findViewById(R.id.player3_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                case 4:
                    ((ImageView) findViewById(R.id.player4_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                case 5:
                    ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.white_dot);
                    break;
                default:

            }
        }
    }

    /**
     * find all people that have folded and set their dot to grey
     * @param indices the list of indices of where the player is display on screen
     */
    @Override
    public void setFolded(ArrayList<Integer> indices){
        runOnUiThread(() -> {
            for(Integer i : indices)
            switch(i){
                case 0:
                    ((ImageView) findViewById(R.id.your_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                case 1:
                    ((ImageView) findViewById(R.id.player1_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                case 2:
                    ((ImageView) findViewById(R.id.player2_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                case 3:
                    ((ImageView) findViewById(R.id.player3_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                case 4:
                    ((ImageView) findViewById(R.id.player4_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                case 5:
                    ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.grey_dot);
                    break;

                default:
                    break;
            }
        });
    }

    /**
     * setting all dots to white and invisible when the game is created
     */
    public void setIdle(){
        runOnUiThread(() -> {
            ((ImageView) findViewById(R.id.your_greendot)).setImageResource(R.drawable.white_dot);
            ((ImageView) findViewById(R.id.player1_greendot)).setImageResource(R.drawable.white_dot);
            ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.white_dot);
            ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.white_dot);
            ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.white_dot);
            ((ImageView) findViewById(R.id.player5_greendot)).setImageResource(R.drawable.white_dot);

            ((ImageView) findViewById(R.id.player1_greendot)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.player2_greendot)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.player3_greendot)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.player4_greendot)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.player5_greendot)).setVisibility(View.INVISIBLE);

        });
    }

    @Override
    public void raiseAmount(int highest_bet) {
        slider.setValueFrom(highest_bet * 2);
    }

    @Override
    public void setHighestBet(int highest_bet) {
        this.highest_bet = highest_bet;
    }

    @Override
    public void setBet(int bet) {
        this.bet = bet;
        if(bet==highest_bet) {
            check.setImageResource(R.drawable.call);
        } else {
            check.setImageResource(R.drawable.check);
        }
    }

    /**
     * sends user to home when websocket is closed.
     *
     * @param msg
     */
    @Override
    public void ToastComments(String msg) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            user.resetUser();
            startActivity(new Intent(GameScreen.this, UserHome.class));
        });
    }


}
