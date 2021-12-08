package com.example.demo1;

import Models.GameInstance;
import Models.User;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.ITextViews;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mock;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoreTests {


    @Mock
    ITextViews views = mock(ITextViews.class);


    @Test
    public void test() throws JSONException, URISyntaxException {
        User user = new User();

        GameInstance instance = new GameInstance(user, views, true);


        assertEquals(instance.getuserList().get(0), user);
    }

    @Test
    public void test2() throws JSONException, URISyntaxException {
        User user = new User();

        GameInstance instance = new GameInstance(user, views, true);

        assertNotNull(instance.getuserList());
    }
}
