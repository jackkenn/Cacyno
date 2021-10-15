package com.example.demo1.api;

import Models.User;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONObject;

public class getUser {
    private User user;
    public User obtainUser(){
    String url = "http://coms-309-046.cs.iastate.edu:8080/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //user = user.JSONtoUser(response);
        }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("FUCK", error.toString());
        }
    });
        return user;
    }
}


