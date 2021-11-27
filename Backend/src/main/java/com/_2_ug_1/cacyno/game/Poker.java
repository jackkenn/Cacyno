package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.models.Game;
import com._2_ug_1.cacyno.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Poker {
    private List<User> _players = new ArrayList<>();
    private List<Integer> _publicCards = new ArrayList<>();
    private PriorityQueue<User> _toPlay = new PriorityQueue<>();
    private PriorityQueue<User> _turnOrder = new PriorityQueue<>();
    private int _blind = 100;
    private Deck _deck;
    private final int _maxPlayers = 12;
    private boolean _gameInit;
    private Game _game;

    public Poker(Game game) {
        _deck = new Deck(this);
        _game = game;
    }

    public boolean initGame() {
        if (_players.size() < 2) //need at least 2 players
            return false;
        _turnOrder.clear();
        _toPlay.clear();
        _game.setRound(0);
        _players.forEach(x -> _turnOrder.add(x));
        _toPlay.addAll(_turnOrder);
        if (_toPlay.poll().getCurrent_game_money() < _blind / 2) {
            removePlayer(_turnOrder.poll()); //TODO: let them know they are too poor to play
            return initGame();
        }
        if (_toPlay.poll().getCurrent_game_money() < _blind) {
            _turnOrder.poll(); //get #2
            removePlayer(_turnOrder.poll()); //TODO: let them know they are too poor to play
            return initGame();
        }
        _toPlay.clear();
        _toPlay.addAll(_turnOrder);
        _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - _blind / 2);
        _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - _blind);
        _game.setPot(_blind + _blind / 2);
        _deck.deal();
        _gameInit = true;
        return true;
    }

    public boolean bet(User u, int bet) {
        if (!_toPlay.peek().getId().equals(u.getId())) //is turn to play
            return false;
        if (_toPlay.peek().getCurrent_game_money() < bet) { //can make play
            return false;
        }
        if (bet >= 0) {
            _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - bet);
        } else {
            _toPlay.poll().setFolded(true);
        }
        endRound();
        return true;
    }

    public void endRound() {
        int notFolded = 0;
        for (User u : _players) {
            if (!u.getFolded())
                notFolded++;
        }
        if (notFolded > 1) //TODO: all ins
            if (_toPlay.size() > 0)
                return;
        if (_game.getRound() > 3 || notFolded < 2) {
            endGame();
            return;
        }
        _deck.dealPublicCards();
        _game.setRound(_game.getRound() + 1);
        for (int i = 0; i < _turnOrder.size(); i++) { //should be clear
            if (!_turnOrder.peek().getFolded()) {
                _toPlay.add(_turnOrder.peek());
            }
            _turnOrder.add(_turnOrder.poll());
        }
    }

    public void endGame() {
        for (int i = _game.getRound(); i < 5; i++) { //deal the rest of the cards
            _deck.dealPublicCards();
        }
        //TODO: add pot to winning hand

        _toPlay.clear();
        _game.setRound(0);
        boolean blindsReady = false;
        while (!blindsReady) {
            if (_players.size() < 2) { //not enough players to play
                _gameInit = false;
                return;
            }
            if (_toPlay.poll().getCurrent_game_money() < _blind / 2) {
                removePlayer(_turnOrder.poll()); //TODO: let them know they are too poor to play
                if (_toPlay.poll().getCurrent_game_money() < _blind) {
                    _turnOrder.poll(); //get #2
                    removePlayer(_turnOrder.poll()); //TODO: let them know they are too poor to play
                    blindsReady = true;
                }
            }
        }
        _game.setPot(_blind + _blind / 2);
        _deck.deal();
    }

    public boolean addPlayer(User u) { // warning will update them
        for (int i = 0; i < _players.size(); i++) {
            if (_players.get(i).getId().equals(u.getId())) {
                _players.set(i, u);
                return true;
            }
        }
        if (_players.size() < _maxPlayers) {
            u.setFolded(true);
            _turnOrder.add(u);
            _players.add(u);
            return true;
        }
        return false;
    } //TODO: add if they join mid game

    public boolean removePlayer(User u) { //TODO update them if they leave
        bet(u, -1); //fold if it is their turn
        boolean removed = false;
        removed = _players.removeIf(x -> x.getId().equals(u.getId()));
        _turnOrder.removeIf(x -> x.getId().equals(u.getId()));
        _toPlay.removeIf(x -> x.getId().equals(u.getId()));
        if (_players.size() < 2)
            _gameInit = false;
        return removed;
    } //TODO: add if they leave mid game

    public boolean getInitialized() {
        return _gameInit;
    }

    public Game getGame() {
        return _game;
    }

    private class Deck {
        private List<Integer> _cards = new ArrayList<>();
        private final Random _rand = new Random();
        private final Poker _poker;

        public Deck(Poker poker) {
            _poker = poker;
            _rand.setSeed(System.nanoTime());
        }

        public void deal() {
            _cards = new ArrayList<>();
            for (int i = 0; i < 52; i++) {
                _cards.add(i);
            }
            for (User u : _poker._players) {
                u.setCard1(_cards.remove(_rand.nextInt(_cards.size())));
                u.setCard2(_cards.remove(_rand.nextInt(_cards.size())));
            }
        }

        public void dealPublicCards() {
            switch (_poker.getGame().getRound()) {
                case 1:
                    _poker._publicCards.set(0, _cards.remove(_rand.nextInt(_cards.size())));
                    _poker._publicCards.set(1, _cards.remove(_rand.nextInt(_cards.size())));
                    _poker._publicCards.set(2, _cards.remove(_rand.nextInt(_cards.size())));
                    break;
                case 2:
                    _poker._publicCards.set(4, _cards.remove(_rand.nextInt(_cards.size())));
                    break;
                case 3:
                    _poker._publicCards.set(5, _cards.remove(_rand.nextInt(_cards.size())));
                    break;
            }
        }
    }
}
