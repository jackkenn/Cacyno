package Models;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class User {
    String username="";
    String id="";
    int money;
    Boolean displayName;
    public User(String username, String id, int money, Boolean displayName){
        this.username = username;
        this.id = id;
        this.money = money;
        this.displayName = displayName;
    }
    public JSONObject usertoJSON(){
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", id);
            postData.put("username", username);
            postData.put("money", money);
            postData.put("displayname", displayName);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return postData;
    }

    public void JSONtoUser(JSONObject response){
        try {
            money = Integer.parseInt(response.getString("money"));
            username = response.getString("username");
            id = response.getString("id");
            displayName = Boolean.parseBoolean(response.getString("displayname"));
        }catch (JSONException e){
            System.out.println("unable to get data");
        }
    }
    public void appendUser(Context con){
        String url = "http://coms-309-046.cs.iastate.edu:8080/user/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONtoUser(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR_FROM_APPEND", error.toString());
            }
        });
        requestQueue.add(request);
    }
}
