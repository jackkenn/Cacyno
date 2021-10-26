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
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;
import interfaces.ServerCallback;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LobbySelector extends AppCompatActivity{
    private ScrollView scroll;
    private LinearLayout layout;
    private ConstraintLayout constraintLayout;
    ArrayList<Lobby> list;
    private ImageButton refresh;
    private ImageButton back;
    ArrayList<Lobby> lobbies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_selector_screen);
        constraintLayout = findViewById(R.id.constraintLayout);
        back = findViewById(R.id.back);
        refresh = constraintLayout.findViewById(R.id.refresh);
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
