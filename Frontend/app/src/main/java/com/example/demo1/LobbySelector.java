package com.example.demo1;

import Models.Lobby;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.annotations.SerializedName;
import interfaces.ServerCallback;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity{
    private ScrollView scroll;
    private LinearLayout layout;
    ArrayList<Lobby> list;
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        Lobby lb = new Lobby();
        list = new ArrayList<>();
        lb.calltoServer(this, list, new ServerCallback(){

            @Override
            public void onSuccess(JSONArray response){
                for(Lobby i : list) {
                    View row = getLayoutInflater().inflate(R.layout.lobby_row, layout);
                }
            }
        });
        layout = findViewById(R.id.linear);
        scroll = findViewById(R.id.scroll);
    }

}
