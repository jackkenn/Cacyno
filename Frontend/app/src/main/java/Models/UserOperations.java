package Models;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class UserOperations {

    public UserOperations(){}

    /**
     * Turns the given JSONObject into a user Object
     * @param response the given JSONObejct
     * @param user the user object to put the fields in
     */
    public void JSONtoUser(JSONObject response, User user, boolean InGame){
        try {
            user.setMoney(Integer.parseInt(response.getString("money")));
            user.setUsername(response.getString("username"));
            user.setId(response.getString("id"));
            user.setDisplayName(Boolean.parseBoolean(response.getString("displayname")));
            user.setCurrent_game_money(Integer.parseInt(response.getString("current_game_money")));
            user.set_spectator(Boolean.parseBoolean(response.getString("isSpectator")));
            if(InGame) {
                user.setGameId(response.getString("game"));

            }
            System.out.println("passed");
            user.setCard1(Integer.parseInt(response.getString("card1")));
            user.setCard2(Integer.parseInt(response.getString("card2")));
            user.setFolded(Boolean.parseBoolean(response.getString("folded")));
            user.setBet(Integer.parseInt(response.getString("bet")));
            user.setPosition(Integer.parseInt(response.getString("position")));
            user.setHas_played(Boolean.parseBoolean(response.getString("hasPlayed")));
        }catch (JSONException e){
            Log.e("ERROR: JSON->USER", e.toString());
        }

    }

    /**
     * turns the given User into a JSONObject
     * @param user
     * @return
     */
    public JSONObject usertoJSON(User user, boolean InGame){
        JSONObject postData = new JSONObject();
        try {
            postData.put("id", user.getId());
            postData.put("username", user.getUsername());
            postData.put("money", user.getMoney());
            postData.put("displayname", user.getDisplayName());
            postData.put("current_game_money", user.getCurrent_game_money());
            postData.put("bet", user.getBet());
            postData.put("card1", user.getCard1());
            postData.put("card2", user.getCard2());
            postData.put("folded", user.getFolded());
            postData.put("isSpecatator", user.getIs_spectator());
            postData.put("position", user.getPosition());
            if(InGame)
                postData.put("game", user.getGameId());
            postData.put("hasPlayed", user.getHas_played());
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return postData;
    }
}
