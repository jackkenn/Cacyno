package com.example.demo1;

import Models.Lobby;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import interfaces.ILobby;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity{
    private ScrollView scroll;
    private LinearLayout layout;
    private ConstraintLayout constraintLayout;
    ArrayList<Lobby> list;
    private ImageButton refresh;
    private ImageButton back;
    private ImageButton creategame;
    private int nextId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        constraintLayout = findViewById(R.id.constraintLayout);
        back = findViewById(R.id.back);
        refresh = constraintLayout.findViewById(R.id.refresh);
        creategame = findViewById(R.id.create);
        Lobby lb = new Lobby();
        list = new ArrayList<>();
        String user = getIntent().getStringExtra("username");
        /*
          wait for lobbies to be appended to list to display on screen
         */
        lb.calltoServer(this, list, new ILobby(){
            /**
             * if lobbies are successfully added to a list of lobby objects, then this method will add a
             * view for each lobby on screen with unique id
             * to be able to join or spectate a game.
             * @return int 0 for success
             */
            @Override
            public int onSuccess(){
                int indexForId = 1;
                for(Lobby i : list) {
                    View newLobbbyRow = getLayoutInflater().inflate(R.layout.lobby_row, layout);
                    TextView lobbyName = findViewById(R.id.lobbyname);
                    ImageButton joinBut = findViewById(R.id.join);
                    ImageButton spectate = findViewById(R.id.spectate);
                    lobbyName.setId(indexForId);
                    indexForId++;
                    lobbyName.setText(i.getLobbyname());
                    nextId = indexForId;
                }
                return 0;
            }
            /**
             * if lobbies are not successfully added to a list of lobby objects
             * @return int -1 for not successful
             */
            @Override
            public int onError(){
                return -1;
            }
        });
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);

        refresh.setOnClickListener(new View.OnClickListener() {
            /**
             * if the refresh button is clicked then all lobbies on screen are removed and a call to endpoint
             * to get all lobbies is made
             * @param view current view of the device
             */
            @Override
            public void onClick(View view) {
                layout.removeAllViews();
                list.removeAll(list);
                lb.calltoServer(view.getContext(), list, new ILobby(){
                    /**
                     * if lobbies are successfully added to a list of lobby objects, then this method will add a
                     * view for each lobby on screen with unique id
                     * to be able to join or spectate a game.
                     * @return int 0 for success
                     */
                    @Override
                    public int onSuccess(){
                        int indexForId = 1;
                        for(Lobby i : list) {
                            View row = getLayoutInflater().inflate(R.layout.lobby_row, layout);
                            TextView lobbyName = findViewById(R.id.lobbyname);
                            lobbyName.setId(indexForId);
                            indexForId++;
                            lobbyName.setText(i.getLobbyname());
                            nextId = indexForId;
                        }
                        return 0;
                    }
                    /**
                     * if lobbies are not successfully added to a list of lobby objects
                     * @return int -1 for not successful
                     */
                    @Override
                    public int onError(){
                        return -1;
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            /**
             * when the back button is clicked, this method will take you to the user home screen.
             * @param view current view of this device
             */
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LobbySelector.this, UserHome.class);
                i.putExtra("username", user);
                startActivity(i);

            }
        });
        creategame.setOnClickListener(new View.OnClickListener() {
            /**
             * when the "create a game" button is clicked, this method will take you to a screen where you will
             * be able to create a new game.
             * @param view current view of this device
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LobbySelector.this, CreateGame.class);
                intent.putExtra("nextid", nextId);
                startActivity(intent);
            }
        });
    }





}
