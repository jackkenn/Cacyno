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

@ServerEndpoint("/poker/{userId}")
@Component

/**
 * TODO: add admin for resets and starting
 */
public class PokerEndpoint {
    private static Map<String, Poker> _gamesMap = new HashMap<>();
    private static Map<String, List<Session>> _gameSessionMap = new HashMap<>();
    private static Map<String, Session> _userSessionMap = new HashMap<>();
    private static Map<Session, String> _sessionUserMap = new HashMap<>();
    private final Logger _logger = LoggerFactory.getLogger(PokerEndpoint.class);

    @Autowired
    IUserRepo _userRepo;

    @Autowired
    IGameRepo _gameRepo;

    @OnOpen
    public void onOpen(Session session
            , @PathParam("userId") String userId) throws IOException {
        _logger.info("Entered into Open: " + userId);
        User u = _userRepo.findById(userId).orElse(null);
        if (u == null || u.getGame().getId().equals("")) //let them know
            throw new NullPointerException();
        Game g = _gameRepo.findById(u.getGame().getId()).orElse(null);
        if (g == null) {
            throw new NullPointerException();
        }
        u.setGame(g);
        if (!_gamesMap.containsKey(u.getGame().getId())) {
            Poker poker = new Poker(g);
            if (!poker.addPlayer(u)) //TODO: assuming they joining to play
                throw new NullPointerException(); //TODO: let them know it is full
            _gamesMap.put(g.getId(), poker);
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
        _userSessionMap.remove(_sessionUserMap.get(session));
        User toRemove = _userRepo.getById(_sessionUserMap.get(session));
        _gamesMap.get(toRemove.getGame().getId()).removePlayer(toRemove);
        _sessionUserMap.remove(session);
        if (_gameSessionMap.get(toRemove.getGame().getId()).size() <= 1) {
            _gameSessionMap.remove(toRemove.getGame().getId());
            _gamesMap.remove(toRemove.getGame().getId());
        } else {
            _gameSessionMap.get(toRemove.getGame().getId()).removeIf(x -> x.getId().equals(toRemove.getId()));
            sendGameMessage(toRemove.getGame().getId(), toRemove.getUsername() + ": Has Left");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        _logger.info("Entered into Message: " + _sessionUserMap.get(session) + ". Got Message: " + message);
        User u = _userRepo.getById(_sessionUserMap.get(session));
        Poker p = _gamesMap.get(u.getGame().getId());
        if (message.equals("initGame"))
            p.initGame();
        if (p.getInitialized()) {
            if (message.substring(0, 4).equals("Bet")) {
                int bet = Integer.parseInt(message.split("^[0-9]*$").toString());
                if (p.bet(u, bet))
                    sendGameMessage(u.getGame().getId(), u.getUsername() + "Has " + message);
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
}
