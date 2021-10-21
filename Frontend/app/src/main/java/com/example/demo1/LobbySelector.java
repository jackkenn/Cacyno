package com.example.demo1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity {
    private ScrollView scroll;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);

        for(int i=0; i<4; i++) {

            TableRow row = new TableRow(this);
            ImageView lobbyBut = new ImageView(this.getApplicationContext());
            ImageButton joinBut = new ImageButton(this.getApplicationContext());
            ImageButton specBut = new ImageButton(this.getApplicationContext());

            setRow(row);

            setLobby(lobbyBut);
            setJoin(joinBut);
            setSpec(specBut);


            row.addView(lobbyBut);
            layout.addView(row);
            layout.addView(joinBut);
            System.out.println("added");
        }

        //TODO api call


    }

    private void setRow(TableRow row){
        row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.row_height)));
        //row.setGravity(Gravity.CENTER_VERTICAL);
        ImageView t = new ImageView(this);
        setLobby(t);
        t.setImageResource(R.drawable.blank_but);
        row.addView(t);
    }

    private void setLobby(ImageView but){
        but.setImageResource(R.drawable.blank_but);
        but.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setJoin(ImageButton but){
        but.setImageResource(R.drawable.join_but);
        but.setLayoutParams(new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.join_width), getResources().getDimensionPixelSize(R.dimen.join_height)));
        but.setBackgroundColor(Color.TRANSPARENT);
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setSpec(ImageButton but){
        but.setImageResource(R.drawable.spectate_but);
        but.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.spec_height)));
        but.setBackgroundColor(Color.TRANSPARENT);
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}
