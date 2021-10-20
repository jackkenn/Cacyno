package com.example.demo1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity {
    private ScrollView scroll;
    private LinearLayout layout;
    ArrayList<Button> al = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);


        for(int i=0; i<10; i++) {
            TableRow row = new TableRow(this.getApplicationContext());
            ImageView lobbyBut = new ImageView(this.getApplicationContext());
            ImageButton joinBut = new ImageButton(this.getApplicationContext());
            ImageButton specBut = new ImageButton(this.getApplicationContext());

            setLobby(lobbyBut);

            layout.addView(row, i);
        }

        //TODO api call


    }
    private void setLobby(ImageView but){

    }
}
