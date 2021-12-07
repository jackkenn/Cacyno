package Models;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.demo1.R;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.ITextViews;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Mainly this class is to help keep game screen organized and connect to the
 * poker websockets.
 */
public class GameInstance{
    WebSocketClient mWebSocketClient;
    private ArrayList<User> users;
    private ITextViews views;
    private int currentPlayerIndex;
    private ArrayList<Integer> indiciesOfFolded;
    /**
     * this constructor makes a game instance object for each player.
     * @param user the user that is using the device
     * @param views callback for updating views on the screen
     * @throws URISyntaxException
     * @throws JSONException
     */
    public GameInstance(User user, ITextViews views) throws URISyntaxException, JSONException {
        connectWebSocket(user);
        users = new ArrayList<>();
        users.add(user);
        this.views = views;
        indiciesOfFolded = new ArrayList<>();
    }

    private void connectWebSocket(User user) throws URISyntaxException, JSONException {
        URI uri;

        //need to change to remote
        uri = new URI("ws://coms-309-046.cs.iastate.edu:8080/poker/" + FirebaseAuth.getInstance().getUid());
//        uri = new URI("ws://coms-309-046.cs.iastate.edu:8080/poker/1");
        //uri = new URI("ws://192.168.1.2:8080/chat/1/2");

        mWebSocketClient = new WebSocketClient(uri) {


            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("connected to " + uri.toString());
            }

            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onMessage(String msg) {
                if (msg.startsWith("[{")){
                    String getPlayersOnly = msg.split("],")[0];
                    getPlayersOnly += "]";
                    String gameStatus = msg.split("],")[1];
                    JSONArray stringToJSON = new JSONArray(getPlayersOnly);
                    JSONObject gameJSON = new JSONObject(gameStatus);

                    for (User i : user.JSONtolist(stringToJSON)) {
                        if (!user.getId().equals(i.getId()) && !checkObjects(i) && users.size() != 6) {
                            users.add(i);
                            if (users.size() == 2)
                                mWebSocketClient.send("START THIS SHIT");
                            currentPlayerIndex++;
                            toView(i);
                        }
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            views.TableCard1(gameJSON.getInt("public_card1"));
                            views.TableCard2(gameJSON.getInt("public_card2"));
                            views.TableCard3(gameJSON.getInt("public_card3"));
                            views.TableCard4(gameJSON.getInt("public_card4"));
                            views.TableCard5(gameJSON.getInt("public_card5"));
                            views.pot(gameJSON.getInt("pot"));
                            views.MyMoney(users.get(0).current_game_money);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    });
                }
                else if(msg.contains("Has Left")){
                    String playerUsername = msg.split(":")[0];
                    for(int i = 0; i < users.size(); i++){
                        if(users.get(i).getUsername().equals(playerUsername)){
                            removePlayer(i);
                            users.remove(i);
                            currentPlayerIndex = i;
                        }
                    }

                }
                //set player green
                new Handler(Looper.getMainLooper()).post(() -> views.setGreen(currentPlayerIndex));

                //find players who folded and set their dot to grey
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getFolded())
                        indiciesOfFolded.add(i);
                }
                new Handler(Looper.getMainLooper()).post(() -> views.setFolded(indiciesOfFolded));
            }

            @Override
            public void onClose ( int errorCode, String reason,boolean remote){
                System.out.println("Connection closed " + uri.toString());
                System.out.println("Connection closed " + remote + reason);
                if (remote)
                    views.ToastComments(reason);
            }

            @Override
            public void onError (Exception e){
                Log.i("Websocket", "Error " + e.getMessage());
            }

        };
        mWebSocketClient.connect();
    }

    /**
     * calling callback method in game screen to set textviews for other
     * players
     * @param user the user data
     */
    private void toView(User user){
        new Handler(Looper.getMainLooper()).post(() -> {
            switch(currentPlayerIndex){
                case 1:
                    views.Player1Username(user.getUsername());
                    views.Player1Money("$"+user.getCurrent_game_money()+"");
                    break;
                case 2:
                    views.Player2Username(user.getUsername());
                    views.Player2Money("$"+user.getCurrent_game_money()+"");
                    break;
                case 3:
                    views.Player3Username(user.getUsername());
                    views.Player3Money("$"+user.getCurrent_game_money()+"");
                    break;
                case 4:
                    views.Player4Username(user.getUsername());
                    views.Player4Money("$"+user.getCurrent_game_money()+"");
                    break;
                case 5:
                    views.Player5Username(user.getUsername());
                    views.Player5Money("$"+user.getCurrent_game_money()+"");
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * removes the player from view
     * @param index the index of the player
     */
    private void removePlayer(int index){
        new Handler(Looper.getMainLooper()).post(() -> {
            switch(index){
                case 1:
                    views.Player1Username("waiting...");
                    views.Player1Money("");
                    break;
                case 2:
                    views.Player2Username("waiting...");
                    views.Player2Money("");
                    break;
                case 3:
                    views.Player3Username("waiting..");
                    views.Player3Money("");
                    break;
                case 4:
                    views.Player4Username("waiting...");
                    views.Player4Money("");
                    break;
                case 5:
                    views.Player5Username("waiting...");
                    views.Player5Money("");
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * helper method to check if the user is already in the arraylist
     * couldn't use .contains due to it being a shallow check
     * @param user the user to check if in the arraylist
     * @return true if user is in the arraylist already, false is not
     */
    private boolean checkObjects(User user){
        for (User i : users){
            if(user.getId().equals(i.getId()))
                return true;
        }
        return false;
    }

    public void send(String message) {
        mWebSocketClient.send(message);
    }

    /**
     * called from game screen class when back button is clicked
     */
    public void closeWebsocket(){
        mWebSocketClient.close();
    }

}
