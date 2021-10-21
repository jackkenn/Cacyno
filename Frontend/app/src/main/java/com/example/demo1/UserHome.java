package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHome extends AppCompatActivity {
    private ImageButton settings;
    private ImageButton play;
    private TextView money;
    private RequestQueue res;
    private TextView username;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        user = new User();
        settings = (ImageButton) findViewById(R.id.settings);
        money = (TextView) findViewById(R.id.moneyRequest);
        username = (TextView) findViewById(R.id.currentUser);
        play = (ImageButton) findViewById(R.id.play_create_but);
        res = Volley.newRequestQueue(this);
        appendUser();
        user.setDisplayName(getIntent().getBooleanExtra("displayname", false));
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this, Settings.class);
                intent.putExtra("displayname", user.getDisplayName());
                startActivity(intent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(UserHome.this, LobbySelector.class);
                startActivity(I);
            }
        });
    }
    /**
     * getting money amount and username from server
     */
    public void appendUser(){
        String url = "http://coms-309-046.cs.iastate.edu:8080/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                user.JSONtoUser(response);
                money.append(user.getMoney()+"");
                username.append(user.getUsername());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });
        res.add(request);
    }
}
