package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHome extends AppCompatActivity {
    private ImageButton settings;
    private TextView money;
    private RequestQueue res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        settings = (ImageButton) findViewById(R.id.settings);
        money = (TextView) findViewById(R.id.moneyRequest);
        res = Volley.newRequestQueue(this);
        JSONParse();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    /**
     * getting money amount from test server
     */
    private void JSONParse(){
        /**
         * replace this url with server
         */
        String url = "https://b673cace-52ff-44cd-98d1-644e53a719da.mock.pstmn.io/money/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String responseJson = response.getString("amount");
                    money.append(responseJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        res.add(request);
    }
}
