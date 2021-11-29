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
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Setter
@Getter
/**
 * this class is the user instance when using the application
 */
public class User {
    String username="";
    String id="";
    int money;
    boolean displayName;
    UserOperations ops;
    int current_game_money;
    boolean is_spectator;
    JSONObject gameId;
    int bet;
    int card1;
    int card2;
    boolean folded;
    boolean has_played;
    int position;
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
    public JSONObject usertoJSON(boolean InGame){
        return ops.usertoJSON(this, InGame);
    }
    /**
     * gets displayName
     * @return displayName
     */
    public boolean getDisplayName() {
        return displayName;
    }

    /**
     * resets the user when leaves or game ends
     */
    public void resetUser(){
        ops.removeAttributes(this);
    }
    /**
     * sets displayName
     */
    public void setDisplayName(boolean b){
        this.displayName = b;
    }
    /**
     * gets folded
     * @return folded
     */
    public boolean getFolded(){
        return folded;
    }
    /**
     * gets is_spectator
     * @return is_spectator
     */
    public boolean getIs_spectator(){
        return is_spectator;
    }
    /**
     * gets has_played
     * @return has_played
     */
    public boolean getHas_played() {
        return has_played;
    }

    /**
     * Converts a JSONObject into our User model
     * @param response the given JSON response
     */
    public void JSONtoUser(JSONObject response, boolean InGame){
       ops.JSONtoUser(response, this, InGame);
    }

    /**
     * makes changes to a user based on the callback
     * @param con context of the app
     * @param callback the changes to be made
     * @param id the id of the user
     */
    public void getUser(Context con, IUser callback, String id, boolean InGame){
        String url = "http://coms-309-046.cs.iastate.edu:8080/user/" + id;
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response + " get user -> " + con);
                JSONtoUser(response, InGame);
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
    public void updateUser(Context con, IUser callback, boolean InGame){
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, usertoJSON(InGame), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response + "updating -> " + con);
                callback.onSuccess();
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR: UPDATING -> " + con, error.toString());
                callback.onError();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * sets base values for the user
     * @param con context of the app
     */
    public void firstTimeAppend(Context con, IUser callback){
        String postUrl = "http://coms-309-046.cs.iastate.edu:8080/user";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            postData.put("username", "");
            postData.put("money", "1000");
            postData.put("displayname", true);
            postData.put("current_game_money", 0);
            postData.put("bet", 0);
            postData.put("card1", 0);
            postData.put("card2", 0);
            postData.put("folded", false);
            postData.put("hasPlayed", false);
            postData.put("isSpectator", false);
            postData.put("position", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess();
                System.out.println(response);
            }
        }, error -> error.printStackTrace());
        requestQueue.add(jsonObjectRequest);
    }
}
