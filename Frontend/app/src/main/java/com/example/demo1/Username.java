package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
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


public class Username extends AppCompatActivity {
    private TextView username;
    private ImageButton apply;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_username_screen);
        user = new User();
        user.appendUser(this);
        username = (TextView) findViewById(R.id.username);
        apply = (ImageButton) findViewById(R.id.apply_but);
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
                    volleyPost();
                    Intent I = new Intent(Username.this, UserHome.class);
                    startActivity(I);
                }
            }
        });
    }
    public void volleyPost() {
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println(user.usertoJSON().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, user.usertoJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR ADDING USERNAME", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
