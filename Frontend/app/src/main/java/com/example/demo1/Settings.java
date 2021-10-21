package com.example.demo1;

import Models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.demo1.api.getUser;

public class Settings extends AppCompatActivity {
    private ImageButton back;
    private ImageButton apply;
    private TextView username;
    private ImageButton logout;
    private Switch displayName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        user = new User();
        user.appendUser(this);
        back = (ImageButton) findViewById(R.id.settings_back);
        apply = (ImageButton) findViewById(R.id.apply_button);
        username = (TextView) findViewById(R.id.editTextTextPersonName);
        logout = (ImageButton) findViewById(R.id.logout_but);
        displayName = (Switch) findViewById(R.id.display_name_toggle);
        displayName.setOnCheckedChangeListener(null);

        if(getIntent().getBooleanExtra("displayname", false))
            displayName.setChecked(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDisplayName();
                Intent intent = new Intent(Settings.this, UserHome.class);
                intent.putExtra("displayname", user.getDisplayName());
                startActivity(intent);
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = username.getText().toString();
                if(input.isEmpty()){
                    username.setError("Please provide username");
                    username.requestFocus();
                }
                else if(input.equals("username")){
                    username.setError("Please provide username");
                    username.requestFocus();
                }
                else{
                    user.setUsername(input);
                    setUsername();
                    Intent I = new Intent(Settings.this, UserHome.class);
                    startActivity(I);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                setDisplayName();
                Intent I = new Intent(Settings.this, ActivityLogin.class);
                startActivity(I);
                Toast.makeText(Settings.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
        displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setDisplayName(!user.getDisplayName());
            }
        });
    }
    public void setUsername(){
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = user.usertoJSON();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, error -> error.printStackTrace());
        requestQueue.add(jsonObjectRequest);
    }

    public void setDisplayName() {
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = user.usertoJSON();
        System.out.println(user.getDisplayName() + "setDisplayname");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, error -> error.printStackTrace());
        requestQueue.add(jsonObjectRequest);
    }

}
