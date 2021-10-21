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
import com.google.android.material.tabs.TabLayout;

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

        for(int i=0; i<10; i++) {

            TableRow row = new TableRow(this);

            setRow(row);

            layout.addView(row);
            System.out.println("added");
        }

        //TODO api call


    }

    private void setRow(TableRow row){
        row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.row_height)));
        row.setGravity(Gravity.CENTER_VERTICAL);
        ImageView lob = new ImageView(this);
        ImageButton join = new ImageButton(this);
        ImageButton spectate = new ImageButton(this);
        setLobby(lob);
        setJoin(join);
        setSpec(spectate);
        row.addView(lob);
        row.addView(join);
        row.addView(spectate);
    }

    private void setLobby(ImageView but){
        but.setImageResource(R.drawable.blank_but);
        but.setLayoutParams(new TableRow.LayoutParams(getResources().getDimensionPixelSize(R.dimen.lobby_width), getResources().getDimensionPixelSize(R.dimen.lobby_height)));
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setJoin(ImageButton but){
        but.setImageResource(R.drawable.join_but);
        but.setLayoutParams(new TableRow.LayoutParams(getResources().getDimensionPixelSize(R.dimen.join_width), getResources().getDimensionPixelSize(R.dimen.join_height)));
        but.setBackgroundColor(Color.TRANSPARENT);
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setSpec(ImageButton but){
        but.setImageResource(R.drawable.spectate_but);
        but.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.spec_height)));
        but.setBackgroundColor(Color.TRANSPARENT);
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}
