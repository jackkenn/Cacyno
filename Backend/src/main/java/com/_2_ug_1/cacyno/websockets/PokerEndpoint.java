package com._2_ug_1.cacyno.websockets;

import com._2_ug_1.cacyno.game.Poker;
import com._2_ug_1.cacyno.models.Game;
import com._2_ug_1.cacyno.models.User;
import com._2_ug_1.cacyno.repos.IGameRepo;
import com._2_ug_1.cacyno.repos.IUserRepo;
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

/**
 * TODO: add admin for resets and starting
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

    @Autowired
    public void setRepos(IUserRepo userRepo, IGameRepo gameRepo) {
        _userRepo = userRepo;
        _gameRepo = gameRepo;
    }

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
        u.setGame(g);
        if (!_gamesMap.containsKey(u.getGame().getId())) {
            Poker poker = new Poker(g);
            if (!poker.addPlayer(u)) //TODO: assuming they joining to play
                throw new NullPointerException(); //TODO: let them know it is full/better error detection
            _gamesMap.put(g.getId(), poker);
        } else {
            if (!_gamesMap.get(u.getGame().getId()).addPlayer(u))
                throw new NullPointerException(); //TODO: let them know it is full/better error detection
        }
        _userRepo.save(u);
        if (_gameSessionMap.containsKey(u.getGame().getId())) {
            _gameSessionMap.get(u.getGame().getId()).add(session);
        } else {
            List<Session> sessionList = new ArrayList<>();
            sessionList.add(session);
            _gameSessionMap.put(u.getGame().getId(), sessionList);
        }
        _sessionUserMap.putIfAbsent(session, userId);
        _userSessionMap.putIfAbsent(userId, session);
        sendGameMessage(g.getId(), u.getUsername() + ": Has Joined");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        _logger.info("Entered into Close: " + _sessionUserMap.get(session));
        if (!_sessionUserMap.containsKey(session))
            return;
        _userSessionMap.remove(_sessionUserMap.get(session));
        User toRemove = getUser(_sessionUserMap.get(session));
        _gamesMap.get(toRemove.getGame().getId()).removePlayer(toRemove);
        _sessionUserMap.remove(session);
        if (_gameSessionMap.get(toRemove.getGame().getId()).size() <= 1) {
            _gameSessionMap.remove(toRemove.getGame().getId());
            _gamesMap.remove(toRemove.getGame().getId());
        } else {
            _gameSessionMap.get(toRemove.getGame().getId()).removeIf(x -> x.equals(session));
            sendGameMessage(toRemove.getGame().getId(), toRemove.getUsername() + ": Has Left");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        _logger.info("Entered into Message: " + _sessionUserMap.get(session) + ". Got Message: " + message);
        User u = getUser(_sessionUserMap.get(session));
        Poker p = _gamesMap.get(u.getGame().getId());
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
}
