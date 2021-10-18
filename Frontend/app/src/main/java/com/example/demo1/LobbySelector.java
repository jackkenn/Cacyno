package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
            Button button = new Button(this.getApplicationContext());
            button.setText("" + i);
            layout.addView(button);
        }

        //TODO api call


    }
}
