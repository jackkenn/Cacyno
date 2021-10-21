package com.example.demo1;

import Models.Lobby;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity {
    private ScrollView scroll;
    private LinearLayout layout;
    private static int i;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);
        Lobby lb = new Lobby();
        ArrayList<Lobby> list = new ArrayList<>();
        lb.calltoServer(this, list);

        for(int i = 0; i < 10; i++) {

            TableRow row = new TableRow(this);

            setRow(row, "jack's lobby");

            layout.addView(row);
            System.out.println("added");
        }

        //TODO api call


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRow(TableRow row, String lobbyName){
        row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.row_height)));
        row.setGravity(Gravity.CENTER_VERTICAL);
        ImageView lob = new ImageView(this);
        ImageButton join = new ImageButton(this);
        ImageButton spectate = new ImageButton(this);

        TextView name = new TextView(this);

        setLobby(lob);
        row.addView(lob);
        setTextView(name, lobbyName);
        row.addView(name);
        row.invalidate();
        setJoin(join);
        setSpec(spectate);
        row.invalidate();

        row.addView(join);
        row.addView(spectate);
    }

    private void setLobby(ImageView but){
        but.setImageResource(R.drawable.blank_but);
        but.setLayoutParams(new TableRow.LayoutParams(getResources().getDimensionPixelSize(R.dimen.lobby_width), getResources().getDimensionPixelSize(R.dimen.lobby_height)));
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTextView(TextView view, String text){
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        view.setText(text);
        view.setTextSize(19);
        view.setTypeface(getResources().getFont(R.font.aqua));
        view.setTextColor(getResources().getColor(R.color.lobbytext));
        view.setTranslationX(getResources().getDimensionPixelSize(R.dimen.translation));
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
