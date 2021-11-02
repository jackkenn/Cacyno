package Models;


import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import interfaces.ILobby;
import interfaces.IUser;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Setter
@Getter
public class Lobby {
    @SerializedName("id")
    String id = "";
    @SerializedName("lobbyname")
    String lobbyname = "";
    @SerializedName("active")
    boolean active;

    LobbyOperations ops;

    public Lobby(){
        ops = new LobbyOperations();
    }

    public boolean getActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }


    public void getUser(Context con, ILobby callback, String id){
        String url = "http://coms-309-046.cs.iastate.edu:8080/lobby/" + id;
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONtoLobby(response);
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

    private ArrayList<Lobby> JSONtolist(JSONArray response){
        Type lobbylist = new TypeToken<ArrayList<Lobby>>(){}.getType();
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        return gson.fromJson(response.toString(), lobbylist);
    }
    public void calltoServer(Context con, ArrayList<Lobby> list, ILobby callback){
        String url = "http://coms-309-046.cs.iastate.edu:8080/lobby";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list.addAll(JSONtolist(response));
                callback.onSuccess();
                System.out.println(list.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR GETTING LOBBIES", error.toString());
            }
        });
        requestQueue.add(request);
    }
    static class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if(ns != null)
                return false;
            return true;
        }
    }
    private void JSONtoLobby(JSONObject response){
        ops.JSONtoLobby(response, this);
    }
    public void calltoServer(Context con, String id){
        String url = "http://coms-309-046.cs.iastate.edu:8080/lobby/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONtoLobby(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR GETTING LOBBY", error.toString());
            }
        });
    }
    private JSONObject LobbyToJSON(){
        return ops.lobbyToJSON(this);

    }
    public void updateLobby(Context con){
        String url = "http://coms-309-046.cs.iastate.edu:8080/lobby";
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, LobbyToJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR sending lobbyTOdb", error.toString());
            }
        });
    }



}
