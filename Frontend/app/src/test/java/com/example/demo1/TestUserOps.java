package com.example.demo1;

import Models.User;
import Models.UserOperations;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUserOps {
    User user = new User();

    @Mock
    JSONObject obj = mock(JSONObject.class);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void gettingUserfromJsonObject() throws JSONException {
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



}