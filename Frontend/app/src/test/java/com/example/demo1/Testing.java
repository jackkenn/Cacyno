package com.example.demo1;

import Models.Lobby;
import Models.LobbyOperations;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Testing {

    Lobby lobby = new Lobby();

    @Mock
    JSONObject obj = mock(JSONObject.class);




    @Test
    public void LobbyFromJSONObjectTest() throws JSONException {
        LobbyOperations ops = new LobbyOperations();
        when(obj.getString("id")).thenReturn("9999");
        when(obj.getString("active")).thenReturn(String.valueOf(true));
        when(obj.getString("lobbyname")).thenReturn("Caden's Lobby");

        ops.JSONtoLobby(obj, lobby);
        assertEquals("9999", lobby.getId());
        assertTrue(lobby.getActive());
        assertEquals("Caden's Lobby", lobby.getLobbyname());
    }

    @Test
    public void LobbyToJSONObjectTest() throws JSONException {
        Lobby lobby = mock(Lobby.class);
        when(lobby.getId()).thenReturn("9999");
        when(lobby.getActive()).thenReturn(Boolean.valueOf("1"));
        when(lobby.getLobbyname()).thenReturn("Caden's Lobby");
        JSONObject tester = new JSONObject();

        tester.put("id", lobby.getId());
        tester.put("active", lobby.getActive());
        tester.put("lobbyname", lobby.getLobbyname());

        LobbyOperations ops = new LobbyOperations();
        assertEquals(tester.getString("id"), ops.lobbyToJSON(lobby).getString("id"));
        assertEquals(tester.getBoolean("active"), ops.lobbyToJSON(lobby).getBoolean("active"));
        assertEquals(tester.get("lobbyname"), ops.lobbyToJSON(lobby).get("lobbyname"));
    }
}