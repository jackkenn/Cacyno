package com.example.demo1;

import Models.Lobby;
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

public class CreateGame extends AppCompatActivity {
    private TextView lobbyname;
    private TextView moneyAmount;
    private ImageButton create;
    private ImageButton back;
    private User user;
    private IUser callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        lobbyname = findViewById(R.id.create_lobbyname);
        moneyAmount = findViewById(R.id.game_money);
        back = findViewById(R.id.back_create_game);
        create = findViewById(R.id.createNewGame);
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
        user.getUser(this, callback, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lobbyInput = lobbyname.getText().toString();
                String money = moneyAmount.getText().toString();
                if(lobbyInput.isEmpty()){
                    lobbyname.setError("please provide lobby name");
                    lobbyname.requestFocus();
                }
                else if(lobbyInput.length() > 12){
                    lobbyname.setError("only 12 characters max please");
                    lobbyname.requestFocus();
                }
                if(money.isEmpty()){
                    moneyAmount.setError("please provide amount");
                    moneyAmount.requestFocus();
                }
                else if(money.matches(".*[a-z].*")){
                    moneyAmount.setError("only digits please");
                    moneyAmount.requestFocus();
                }
                else if(money.charAt(0) == '0'){
                    moneyAmount.setError("real amount of money");
                    moneyAmount.requestFocus();
                }
                else{
                    Lobby newLobby = new Lobby();
                    newLobby.setLobbyname(lobbyInput);
                    newLobby.setActive(true);
                    newLobby.setId(getIntent().getIntExtra("nextid", 1)+"");
                    user.setCurrent_game_money(Integer.parseInt(money));
                    user.updateUser(CreateGame.this, callback);
                    newLobby.newLobby(CreateGame.this);
                    Intent intent = new Intent(CreateGame.this, LobbySelector.class);
                    startActivity(intent);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateGame.this, LobbySelector.class);
                startActivity(intent);
            }
        });

    }
}
