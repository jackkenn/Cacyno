package com.example.demo1;

import Models.User;
import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {

    @Mock
    User user;

    @Mock
    Context context;
    @Test
    public void testSetUsername() {
        Settings settings = new Settings();
        settings.username.setText("adsf");
        settings.apply.setPressed(true);
        String result = user.getUsername();
        assertEquals("asdf", result);
    }
}