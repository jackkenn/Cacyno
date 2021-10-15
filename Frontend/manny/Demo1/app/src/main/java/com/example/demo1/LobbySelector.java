package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity {
    private ScrollView scroll;
    ArrayList<Button> al = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        scroll = findViewById(R.id.scroll);

        al.add(new Button(this.getApplicationContext()));
        al.add(new Button(this.getApplicationContext()));
        al.add(new Button(this.getApplicationContext()));


        //TODO api call

        //scroll.addChildrenForAccessibility(new ArrayList<View>(al));

    }
}
