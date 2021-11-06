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
import interfaces.IUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Setter
@Getter
public class User {
    String username="";
    String id="";
    int money;
    boolean displayName;
    UserOperations ops;
    int current_game_money;

    /**
     * Constructor for making a new User
     */
    public User(){
        ops = new UserOperations();
    }

    /**
     *
     * this method is called when the user object needs to translate into a JSONObject to append to endpoint
     * @return a JSONObject that contains this user object instance
     */
    public JSONObject usertoJSON(){
        return ops.usertoJSON(this);
    }

    /**
     * gets displayName
     * @return displayName
     */
    public boolean getDisplayName() {
        return displayName;
    }

    /**
     * sets displayName
     */
    public void setDisplayName(boolean b){
        this.displayName = b;
    }

    /**
     * Converts a JSONObject into our User model
     * @param response the given JSON response
     */
    public void JSONtoUser(JSONObject response){
       ops.JSONtoUser(response, this);
    }

    /**
     * makes changes to a user based on the callback
     * @param con context of the app
     * @param callback the changes to be made
     * @param id the id of the user
     */
    public void getUser(Context con, IUser callback, String id){
        String url = "http://coms-309-046.cs.iastate.edu:8080/user/" + id;
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONtoUser(response);
                callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        });
        requestQueue.add(request);
    }

    /**
     * updates the user table on the backend
     * @param con context of the app
     * @param callback contains the onSuccess and on Error methods for the call
     */
    public void updateUser(Context con, IUser callback){
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        System.out.println(usertoJSON().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, usertoJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                callback.onSuccess();
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR: UPDATING", error.toString());
                callback.onError();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * sets base values for the user
     * @param con context of the app
     */
    public void firstTimeAppend(Context con){
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            postData.put("username", "");
            postData.put("money", "1000");
            postData.put("displayname", false);
            postData.put("current_game_money", 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, error -> error.printStackTrace());
        requestQueue.add(jsonObjectRequest);
    }
}
