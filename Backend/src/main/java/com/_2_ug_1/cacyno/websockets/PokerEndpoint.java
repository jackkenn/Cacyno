package com._2_ug_1.cacyno.websockets;

import com._2_ug_1.cacyno.game.Poker;
import com._2_ug_1.cacyno.models.Game;
import com._2_ug_1.cacyno.models.User;
import com._2_ug_1.cacyno.repos.IGameRepo;
import com._2_ug_1.cacyno.repos.IUserRepo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;





/*
 * TODO: add admin for resets and starting
 */

/**
 * Creates connection between players and the server allowing for calls to a game object to play poker
 */
@ServerEndpoint("/poker/{userId}")
@Component
public class PokerEndpoint {
    private static Map<String, Poker> _gamesMap = new HashMap<>();
    private static Map<String, List<Session>> _gameSessionMap = new HashMap<>();
    private static Map<String, Session> _userSessionMap = new HashMap<>();
    private static Map<Session, String> _sessionUserMap = new HashMap<>();
    private final Logger _logger = LoggerFactory.getLogger(PokerEndpoint.class);

    private static IGameRepo _gameRepo;
    private static IUserRepo _userRepo;

    /**
     * sets the user and game repositories to pull information from
     *
     * @param userRepo the user repo
     * @param gameRepo the game repo
     */
    @Autowired
    public void setRepos(IUserRepo userRepo, IGameRepo gameRepo) {
        _userRepo = userRepo;
        _gameRepo = gameRepo;
    }

    /**
     * Creates the user's connection to the server and adds them to a specified game
     *
     * @param session user's session
     * @param userId  The id for a user in the user repo
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session
            , @PathParam("userId") String userId) throws IOException {
        _logger.info("Entered into Open: " + userId);
        User u = getUser(userId);
        if (u == null) //let them know
            throw new NullPointerException();
        Game g = getGame(u.getGame().getId());
        if (g == null) {
            throw new NullPointerException();
        }
        g.setActive(true);
        _gameRepo.save(g);
        u.setGame(g);
        if (!_gamesMap.containsKey(u.getGame().getId())) {
            Poker poker = new Poker(g);
            if (!poker.addPlayer(u)) //TODO: assuming they joining to play
                sendUserMessage(u.getId(), u.getUsername() + ": cannot join due to maximum spots already filled"); //TODO: let them know it is full/better error detection //done?
            _gamesMap.put(g.getId(), poker);
        } else {
            if (!_gamesMap.get(u.getGame().getId()).addPlayer(u))
                sendUserMessage(u.getId(), u.getUsername() + ": cannot join due to maximum spots already filled"); //TODO: let them know it is full/better error detection //done?
        }
        _userRepo.save(u);
        if (_gameSessionMap.containsKey(u.getGame().getId())) {
            _gameSessionMap.get(u.getGame().getId()).add(session);
        } else {
            List<Session> sessionList = new ArrayList<>();
            sessionList.add(session);
            _gameSessionMap.put(u.getGame().getId(), sessionList);
        }
        Poker poker = _gamesMap.get(u.getGame().getId());
        sendGameMessage(u.getGame().getId(), getJsonPlayers(poker) + ", " + getJsonGame(poker));
        _sessionUserMap.putIfAbsent(session, userId);
        _userSessionMap.putIfAbsent(userId, session);
    }

    /*
     * TODO: update user and game(if needed) in repo/database
     */

    /**
     * Closes a user's session, removes them from the game and updates database
     *
     * @param session user's session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        _logger.info("Entered into Close: " + _sessionUserMap.get(session));
        if (!_sessionUserMap.containsKey(session))
            return;
        User toRemove = getUser(_sessionUserMap.get(session));
        Poker p = _gamesMap.get(toRemove.getGame().getId());
        if (p.TooPoor().contains(toRemove)) {
            sendUserMessage(toRemove.getId(), toRemove.getUsername() + "Has Been Kicked Due To Insufficient Funds");
        }
        _userSessionMap.remove(_sessionUserMap.get(session));
        _gamesMap.get(toRemove.getGame().getId()).removePlayer(toRemove);
        _sessionUserMap.remove(session);
        if (_gameSessionMap.get(toRemove.getGame().getId()).size() <= 1) {
            p.getGame().setActive(false);
            _gameRepo.save(p.getGame());
            _gameSessionMap.remove(toRemove.getGame().getId());
            _gamesMap.remove(toRemove.getGame().getId());
        } else {
            _gameSessionMap.get(toRemove.getGame().getId()).removeIf(x -> x.equals(session));
            sendGameMessage(toRemove.getGame().getId(), toRemove.getUsername() + ": Has Left");
        }
        _userRepo.save(toRemove);
    }

    /*
     * TODO: broad cast a json of: current game model,  user model who played, list of all players in game. after a
     *  player makes a successful play
     * TODO: enforce/create a formated message system to receive player's plays
     */

    /**
     * receives player's moves for the game and sends out current game model,  user model who played,\
     * list of all players in game. after a player makes a successful play
     *
     * @param session user's session
     * @param message user's playing action
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        _logger.info("Entered into Message: " + _sessionUserMap.get(session) + ". Got Message: " + message);
        User u = getUser(_sessionUserMap.get(session));
        Poker p = _gamesMap.get(u.getGame().getId());
        sendGameMessage(u.getGame().getId(), getJsonPlayers(p) + ", " + getJsonGame(p));
        if (message.equalsIgnoreCase("initGame") && !p.initGame())
            p.initGame();
        if (p.getInitialized()) {
            if (message.substring(0, 3).equalsIgnoreCase("Bet")) {
                String tmp = message.split(" ")[1];
                int bet = Integer.parseInt(tmp); //read only int
                if (p.bet(u, bet)) {
                    sendGameMessage(u.getGame().getId(), u.getUsername() + " has " + message);
                    sendGameMessage(u.getGame().getId(), "Round: " + p.getGame().getRound() + "\n Pot: "
                            + p.getGame().getPot());
                }

            } else {
                sendGameMessage(u.getGame().getId(), u.getUsername() + ": " + message);
            }
        }
    }


    /**
     * reports errors
     *
     * @param session   user's session
     * @param throwable reported exception
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        _logger.info("Entered into Error. " + throwable.getMessage());
    }

    private void sendGameMessage(String gameId, String message) {
        _gameSessionMap.get(gameId).forEach(x -> {
            synchronized (x) {
                try {
                    x.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendUserMessage(String userId, String message) {
        try {
            _userSessionMap.get(userId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private User getUser(String userId) {
        Future<User> u = null;
        try { //waiting for responce
            u = _userRepo.AsyncGetById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return u.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Game getGame(String gameId) {
        Future<Game> g = null;
        try { //waiting for responce
            g = _gameRepo.AsyncGetById(gameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return g.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJsonPlayers(Poker p) {
        List players = p.getPlayers();
        Gson gson = new Gson();
        String playersJson = gson.toJson(players);

        return playersJson;
    }

    private String getJsonGame(Poker p) {
        Game game = p.getGame();
        Gson gson = new Gson();
        String gameJson = gson.toJson(game);

        return gameJson;
    }
}
