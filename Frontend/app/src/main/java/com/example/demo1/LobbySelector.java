package com.example.demo1;

import Models.Lobby;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import interfaces.ServerCallback;
import org.json.JSONArray;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity{
    private ScrollView scroll;
    private LinearLayout layout;
    private ConstraintLayout constraintLayout;
    ArrayList<Lobby> list;
    private ImageButton refresh;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        constraintLayout = findViewById(R.id.constraintLayout);
        back = findViewById(R.id.back);
        refresh = constraintLayout.findViewById(R.id.refresh);
        Lobby lb = new Lobby();
        list = new ArrayList<>();
        /*
          wait for lobbies to be appended to list to display on screen
         */
        lb.calltoServer(this, list, new ServerCallback(){
            @Override
            public void onSuccess(JSONArray response){
                int indexForId = 1;
                for(Lobby i : list) {
                    View newLobbbyRow = getLayoutInflater().inflate(R.layout.lobby_row, layout);
                    TextView lobbyName = findViewById(R.id.lobbyname);
                    lobbyName.setId(indexForId);
                    indexForId++;
                    lobbyName.setText(i.getLobbyname());
                }
            }
        });
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.removeAllViews();
                list.removeAll(list);
                lb.calltoServer(view.getContext(), list, new ServerCallback(){
                    @Override
                    public void onSuccess(JSONArray response){
                        for(Lobby i : list) {
                            View row = getLayoutInflater().inflate(R.layout.lobby_row, layout);
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LobbySelector.this, UserHome.class));
            }
        });
    }





}
