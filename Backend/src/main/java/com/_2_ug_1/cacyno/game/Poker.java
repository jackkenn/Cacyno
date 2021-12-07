package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.models.Game;
import com._2_ug_1.cacyno.models.User;

import java.util.*;

/**
 * calculates game states based on user input
 */
public class Poker {
    private LinkedList<User> _players;
    private Queue<User> _toPlay;
    private LinkedList<User> _turnOrder;
    private int _blind = 50;
    private Deck _deck;
    private final int _maxPlayers = 12;
    private boolean _gameInit;
    private Game _game;
    private List<User> _tooPoor;

    /**
     * constructor for Poker
     *
     * @param game game model from game repo
     */
    public Poker(Game game) {
        _game = game;
        _deck = new Deck(this);
        _turnOrder = new LinkedList<>();
        _toPlay = new LinkedList<>();
        _players = new LinkedList<>();
        game.setHighest_bet(0);
        _tooPoor = new ArrayList<>();
    }

    /**
     * initializes the game, and starts the first round
     *
     * @return a boolean on if the game was successfully started
     */
    public boolean initGame() {
        if (_players.size() < 2) //need at least 2 players
            return false;
        _turnOrder.clear();
        _toPlay.clear();
        _game.setRound(0);
        _players.forEach(x -> {
            x.setFolded(false);
            _turnOrder.add(x);
        });
        _toPlay.addAll(_turnOrder);

        for (int i = 0; i < _turnOrder.size(); i++) {
            if (_toPlay.size() == 2) {
                break;
            } else {
                _toPlay.remove();
            }
        }

        if (_toPlay.peek().getCurrent_game_money() < _blind) {
            _tooPoor.add(_toPlay.peek());
            removePlayer(_toPlay.poll());

            return initGame();
        }
        if (_toPlay.peek().getCurrent_game_money() < _blind * 2) {
            _toPlay.poll(); //get #2
            _tooPoor.add(_toPlay.peek());
            removePlayer(_toPlay.poll());

            return initGame();
        }
        _toPlay.clear();
        _toPlay.addAll(_turnOrder);
        for (int i = 0; i < _turnOrder.size(); i++) {
            if (_toPlay.size() == 2) {
                _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - _blind);
                _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - _blind * 2);
                break;
            } else {
                _toPlay.remove();
            }
        }
        _toPlay.clear();
        _toPlay.addAll(_turnOrder);

        _game.setHighest_bet(_blind * 2);


        _game.setPot(_blind + _blind * 2);
        _deck.deal();
        _gameInit = true;
        return true;
    }

    /**
     * The input from the users to process the next state of the game
     *
     * @param u   user attempting to play
     * @param bet the amount/action the user is making. bet >= 0 will attempt to increase the user's bet, -1 will fold
     * @return a boolean detailing if the user's play was legal/successful and that the game moved to a new state
     */
    public boolean bet(User u, int bet) {
        if (_toPlay.size() < 1 || !_toPlay.peek().getId().equals(u.getId())) //is turn to play
            return false;
        if (_toPlay.peek().getCurrent_game_money() < bet) { //can make play
            return false;
        }
        if(_toPlay.peek().getCurrent_game_money() == bet){
            _toPlay.peek().setAllIn(true);
        }

        if (bet >= 0) {
            _toPlay.peek().setBet(_toPlay.peek().getBet() + bet);
            if (_toPlay.peek().getBet() < _game.getHighest_bet()) {
                if(_toPlay.peek().isAllIn()){
                    _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - bet);
                    _game.setPot(_game.getPot() + bet);
                }
                else{
                    return false;
                }
            } else if (_toPlay.peek().getBet() == _game.getHighest_bet()) {//Call
                _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - bet);
                _game.setPot(_game.getPot() + bet);
            } else {//New highest bet

                LinkedList<User> tempList = new LinkedList<>();
                tempList.clear();
                tempList.addAll(_turnOrder);

                while(tempList.peek().getId().equals(_toPlay.peek().getId())){//should never infinite loop
                    tempList.add(tempList.poll());
                }

                tempList.removeIf(x->_toPlay.contains(x) || x.getFolded() || x.isAllIn());

                _toPlay.addAll(tempList);

                _game.setHighest_bet(_toPlay.peek().getBet());

                _toPlay.peek().setCurrent_game_money(_toPlay.poll().getCurrent_game_money() - bet);
                _game.setPot(_game.getPot() + bet);
            }
        } else {
            _toPlay.poll().setFolded(true);
        }

        endRound();
        return true;
    }

    private void endRound() {
        int notFoldedOrAllIn = 0;
        for (User u : _players) {
            if (!u.getFolded() || !u.isAllIn())
                notFoldedOrAllIn++;
        }


        if (_toPlay.size() > 0 && notFoldedOrAllIn > 1) //dont end if people need to play
            return;
        if (_game.getRound() > 3 || notFoldedOrAllIn < 2) {
            endGame(); //infinite loop if everyone folds, should never happen
            return;
        }
        _deck.dealPublicCards();
        _game.setRound(_game.getRound() + 1);
        for (int i = 0; i < _turnOrder.size(); i++) { //should be clear
            if (!_turnOrder.get(i).getFolded() || !_turnOrder.get(i).isAllIn()) {
                _toPlay.add(_turnOrder.get(i));
            }
        }
    }

    private void endGame() {
        for (int i = _game.getRound(); i < 5; i++) { //deal the rest of the cards
            _game.setRound(i);
            _deck.dealPublicCards();
        }
        //TODO: add pot to winning hand

        _game.setRound(0);
        _players.forEach(x -> x.setFolded(false));
        _players.forEach(x -> x.setAllIn(false));
        _turnOrder.add(_turnOrder.poll()); //change big blind
        _toPlay.clear();
        _toPlay.addAll(_turnOrder);
        boolean blindsReady = false;
        while (!blindsReady) {
            if (_players.size() < 2) { //not enough players to play
                _gameInit = false;
                return;
            }
            if (_toPlay.peek().getCurrent_game_money() < _blind) {
                _tooPoor.add(_turnOrder.peek());
                removePlayer(_turnOrder.poll());
            } else if (_toPlay.peek().getCurrent_game_money() < _blind * 2) {
                _turnOrder.poll(); //get #2
                _tooPoor.add(_turnOrder.peek());
                removePlayer(_turnOrder.poll());
            } else {
                blindsReady = true;
            }
        }
        _toPlay.clear();
        _toPlay.addAll(_turnOrder);
        _game.setHighest_bet(_blind * 2);
        _game.setPot(_blind + _blind * 2);
        _deck.deal();
    }

    /**
     * adds a user to the game
     *
     * @param u user to be added
     * @return boolean for if the add was legal/successful
     */
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

    /**
     * removes a user from the game
     *
     * @param u user to be removed
     * @return boolean for if the remove was legal/successful
     */
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

    /**
     * gets if the game was initialized
     *
     * @return boolean for if the game was initialized
     */
    public boolean getInitialized() {
        return _gameInit;
    }

    /**
     * gets the model of the game
     *
     * @return the model of the game
     */
    public Game getGame() {
        return _game;
    }

    public List TooPoor() {
        return _tooPoor;
    }

    public List getPlayers() {
        return _players;
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
                    _poker._game.setPublic_card1(_cards.remove(_rand.nextInt(_cards.size())));
                    _poker._game.setPublic_card2(_cards.remove(_rand.nextInt(_cards.size())));
                    _poker._game.setPublic_card3(_cards.remove(_rand.nextInt(_cards.size())));
                    break;
                case 2:
                    _poker._game.setPublic_card4(_cards.remove(_rand.nextInt(_cards.size())));
                    break;
                case 3:
                    _poker._game.setPublic_card5(_cards.remove(_rand.nextInt(_cards.size())));
                    break;
            }
        }
    }
}
