package com.example.demo1;

import Models.User;
import Models.UserOperations;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper;
import interfaces.IUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;


import java.net.ConnectException;
import org.mockito.Mockito;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

        ops.JSONtoUser(obj, user);
        assertFalse(user.getDisplayName());
        assertEquals("manny", user.getUsername());
        assertEquals(1000, user.getMoney());
        assertEquals("123", user.getId());

    }
    @Test
    public void usertoJsonObject() throws JSONException {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("manny");
        when(user.getMoney()).thenReturn(1000);
        when(user.getId()).thenReturn("123");
        when(user.getDisplayName()).thenReturn(false);
        JSONObject tester = new JSONObject();

        tester.put("id", user.getId());
        tester.put("username", user.getUsername());
        tester.put("money", user.getMoney());
        tester.put("displayname", user.getDisplayName());

        UserOperations ops = new UserOperations();
        assertEquals(tester.getString("username"), ops.usertoJSON(user).getString("username"));
        assertEquals(tester.get("money"), ops.usertoJSON(user).get("money"));
        assertEquals(tester.getString("id"), ops.usertoJSON(user).getString("id"));
        assertEquals(tester.get("displayname"), ops.usertoJSON(user).get("displayname"));
    }



}