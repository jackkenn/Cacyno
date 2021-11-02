package Models;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class UserOperations {

    public UserOperations(){}

    public void JSONtoUser(JSONObject response, User user){
        try {
            user.setMoney(Integer.parseInt(response.getString("money")));
            user.setUsername(response.getString("username"));
            user.setId(response.getString("id"));
            user.setDisplayName(Boolean.parseBoolean(response.getString("displayname")));
            user.setCurrent_game_money(Integer.parseInt(response.getString("current_game_money")));
        }catch (JSONException e){
            Log.e("ERROR: JSON->USER", e.toString());
        }

    }

    public JSONObject usertoJSON(User user){
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", user.getId());
            postData.put("username", user.getUsername());
            postData.put("money", user.getMoney());
            postData.put("displayname", user.getDisplayName());
            postData.put("current_game_money", user.getCurrent_game_money());
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return postData;
    }
}
