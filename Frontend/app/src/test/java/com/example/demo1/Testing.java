package com.example.demo1;

import Models.User;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Testing {

    @Mock
    User user;

    @Test
    public void testSetUsername() {
        Settings settings = new Settings();
        settings.username.setText("adsf");
        settings.apply.setPressed(true);
        String result = user.getUsername();
        assertEquals("asdf", result);
    }
}