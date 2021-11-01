package Models;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class LobbyOperations {

    public LobbyOperations(){}

    public void JSONtoLobby(JSONObject response, Lobby lobby){
        try {
            lobby.setId(response.getString("id"));
            lobby.setActive(Boolean.parseBoolean(response.getString("active")));
            lobby.setLobbyname(response.getString("lobbyname"));
        }catch (JSONException e){
            Log.e("ERROR: JSON->Lobby", e.toString());
        }

    }

    public JSONObject lobbyToJSON(Lobby lobby){
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", lobby.getId());
            postData.put("active", lobby.getActive());
            postData.put("lobbyname", lobby.getLobbyname());
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return postData;
    }

}
