package com.example.demo1;

import Models.User;
import android.content.Context;
import interfaces.IUser;
import org.junit.Test;
import org.mockito.MockedConstruction;


import java.net.ConnectException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUser {
    User user = null;
    IUser service = mock(IUser.class);
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void setting_username(){
        user = new User();
        user.setUsername("cacyno");
        assertEquals("cacyno", user.getUsername());
    }




}