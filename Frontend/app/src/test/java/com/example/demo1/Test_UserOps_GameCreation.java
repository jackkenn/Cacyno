package com.example.demo1;

import Models.User;
import Models.UserOperations;
import Utilities.GameChecker;
import Utilities.GameCreation;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test_UserOps_GameCreation {

    @Mock
    JSONObject obj = mock(JSONObject.class);

    @Mock
    TextView lobby = mock(EditText.class);

    @Mock
    TextView money = mock(EditText.class);

    @Mock
    Context context = mock(Context.class);

    @Test
    public void gettingUserfromJsonObject() throws JSONException {
        User user = new User();
        UserOperations ops = new UserOperations();
        when(obj.getString("username")).thenReturn("manny");
        when(obj.getString("money")).thenReturn("1000");
        when(obj.getString("id")).thenReturn("123");
        when(obj.getString("displayname")).thenReturn("false");
        when(obj.getString("current_game_money")).thenReturn("0");
        ops.JSONtoUser(obj, user);
        assertFalse(user.getDisplayName());
        assertEquals("manny", user.getUsername());
        assertEquals(1000, user.getMoney());
        assertEquals("123", user.getId());
        assertEquals(0, user.getCurrent_game_money());

    }
    @Test
    public void usertoJsonObject() throws JSONException {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("manny");
        when(user.getMoney()).thenReturn(1000);
        when(user.getId()).thenReturn("123");
        when(user.getDisplayName()).thenReturn(false);
        when(user.getCurrent_game_money()).thenReturn(0);
        JSONObject tester = new JSONObject();

        tester.put("id", user.getId());
        tester.put("username", user.getUsername());
        tester.put("money", user.getMoney());
        tester.put("displayname", user.getDisplayName());
        tester.put("current_game_money", user.getCurrent_game_money());
        UserOperations ops = new UserOperations();

        assertEquals(tester.getString("username"), ops.usertoJSON(user).getString("username"));
        assertEquals(tester.get("money"), ops.usertoJSON(user).get("money"));
        assertEquals(tester.getString("id"), ops.usertoJSON(user).getString("id"));
        assertEquals(tester.get("displayname"), ops.usertoJSON(user).get("displayname"));
        assertEquals(tester.get("current_game_money"), ops.usertoJSON(user).get("current_game_money"));
    }
    @Test
    public void gameCreateEmpty(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(0);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("", lobby)).thenReturn(false);
        when(gameChecker.checkMoneyAmount("", money, user)).thenReturn(false);

        GameCreation game = new GameCreation(gameChecker);

        assertFalse(game.createGame("", "", lobby, money, context, user));
    }

    @Test
    public void gameCreateTooLong(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(100);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("lobbynameistoolong", lobby)).thenReturn(false);
        when(gameChecker.checkMoneyAmount("100", money, user)).thenReturn(true);

        GameCreation game = new GameCreation(gameChecker);

        assertFalse(game.createGame("lobbynameistoolong", "100", lobby, money, context, user));
    }

    @Test
    public void gameCreateMoneyCheck(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(100);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("new lobby", lobby)).thenReturn(true);
        when(gameChecker.checkMoneyAmount("1d00", money, user)).thenReturn(false);

        GameCreation game = new GameCreation(gameChecker);

        assertFalse(game.createGame("new lobby", "1d00", lobby, money, context, user));
    }

    @Test
    public void gameCreateMoneyCheck2(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(100);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("new lobby", lobby)).thenReturn(true);
        when(gameChecker.checkMoneyAmount("0100", money, user)).thenReturn(false);

        GameCreation game = new GameCreation(gameChecker);

        assertFalse(game.createGame("new lobby", "1d00", lobby, money, context, user));
    }

    @Test
    public void gameCreateMoneyCheck3(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(0);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("new lobby", lobby)).thenReturn(true);
        when(gameChecker.checkMoneyAmount("100", money, user)).thenReturn(false);

        GameCreation game = new GameCreation(gameChecker);

        assertFalse(game.createGame("new lobby", "1d00", lobby, money, context, user));
    }

    @Test
    public void gameCreateCorrect(){
        GameChecker gameChecker = mock(GameChecker.class);
        User user = mock(User.class);

        when(user.getMoney()).thenReturn(200);
        when(user.getUsername()).thenReturn("manny");

        when(gameChecker.checkLobbyName("new lobby", lobby)).thenReturn(true);
        when(gameChecker.checkMoneyAmount("100", money, user)).thenReturn(true);

        GameCreation game = new GameCreation(gameChecker);

        assertTrue(game.createGame("new lobby", "100", lobby, money, context, user));
    }





}