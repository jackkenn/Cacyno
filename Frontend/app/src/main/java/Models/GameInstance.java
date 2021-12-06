package Models;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import interfaces.ITextViews;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Mainly this class is to help keep game screen organized and connect to the
 * poker websockets.
 */
public class GameInstance{

    private ArrayList<User> users;
    private ITextViews views;
    private int currentPlayerIndex;

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
        currentPlayerIndex++;
    }

    private void connectWebSocket(User user) throws URISyntaxException, JSONException {
        URI uri;

        //need to change to remote
        uri = new URI("ws://coms-309-046.cs.iastate.edu:8080/poker/"+user.getGameId().getString("id")+"/"+user.getUsername());;
        //uri = new URI("ws://192.168.1.2:8080/chat/1/2");

        WebSocketClient mWebSocketClient = new WebSocketClient(uri) {


            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("connected");
            }

            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onMessage(String msg) {

                String getPlayersOnly = msg.split(",")[0];
                JSONArray stringToJSON = new JSONArray(getPlayersOnly);

                for (User i : user.JSONtolist(stringToJSON)) {
                    if (!user.getId().equals(i.getId()) && !checkObjects(i)) {
                        users.add(i);
                        toView(i);
                        currentPlayerIndex++;
                    }

                }

            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                views.ToastComments(reason);
            }

            @Override
            public void onError(Exception e) {
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
        switch(currentPlayerIndex){
            case 1:
                views.Player1Money(user.getUsername());
                views.Player1Money(String.valueOf(user.getMoney()));
                break;
            case 2:
                views.Player2Money(user.getUsername());
                views.Player2Money(String.valueOf(user.getMoney()));
                break;
            case 3:
                views.Player3Money(user.getUsername());
                views.Player3Money(String.valueOf(user.getMoney()));
                break;
            case 4:
                views.Player4Money(user.getUsername());
                views.Player4Money(String.valueOf(user.getMoney()));
                break;
            case 5:
                views.Player5Money(user.getUsername());
                views.Player5Money(String.valueOf(user.getMoney()));
                break;
            default:
                break;
        }
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

}
