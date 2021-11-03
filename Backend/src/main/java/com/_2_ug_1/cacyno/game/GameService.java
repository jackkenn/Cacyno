package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.user.IUserRepo;
import com._2_ug_1.cacyno.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Component("IGameService")
public class GameService implements IGameService {
    @Autowired
    private IGameRepo _gameRepo;
    @Autowired
    private IUserRepo _userRepo;

    @Autowired
    public GameService(IGameRepo _gameRepo, IUserRepo _userRepo) {
        this._gameRepo = _gameRepo;
        this._userRepo = _userRepo;
    }

    @Override
    public Game gameInit(String gameId) {
        Game game = null;
        List<User> users = null;
        try { //need to wait for database
            game = _gameRepo.AsyncGetById(gameId).get();
            users = _userRepo.getAllByGameId(gameId).get();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        if (game == null || users == null) {
            return null; //handle this better
        }

        Deck deck = new Deck();
        game.setPot(0);
        game.setRound(0);
        int i = 0;
        for (User u : deck.Deal(users)) {
            u.setPosition(i++);
            u.setFolded(false);
            u.setHasPlayed(false);
            _userRepo.save(u);
        }
        return _gameRepo.save(deck.DealPublicCards(game));
    }

    @Override
    public Game play(String gameId, String userId, int bet) {
        Game game = null;
        List<User> users = null;
        try { //need to wait for database
            game = _gameRepo.AsyncGetById(gameId).get();
            users = _userRepo.getAllByGameId(gameId).get();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        if (game == null || users == null) {
            return null; //handle this better
        }

        users.sort(new Comparator<User>() { //order for turns
            @Override
            public int compare(User o1, User o2) {
                return o1.getPosition() - o2.getPosition();
            }
        });

        if (game.getRound() < 4) { //check if final round
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                if (!u.getHasPlayed() && !u.getFolded()) { //check turn order
                    if (u.getId().equals(userId) && bet <= u.getCurrent_game_money()) { //check if players turn and can make bet
                        if (bet < 0) { //todo: edge case last person folds
                            u.setFolded(true);
                        } else {
                            u.setBet(u.getBet() + bet);
                            game.setPot(game.getPot() + bet);
                            u.setCurrent_game_money(u.getCurrent_game_money() - bet);
                        }
                        u.setHasPlayed(true);
                        _userRepo.save(u);
                        for (int j = i + 1; j < users.size(); j++) { //is last person to play
                            if (!users.get(j).getFolded()) {
                                return _gameRepo.save(game);
                            }
                        }
                        game.setRound(game.getRound() + 1); //end round if last playing
                        if (game.getRound() < 4) {
                            for (User v : users) {
                                v.setHasPlayed(false);
                                _userRepo.save(v);
                            }
                            return _gameRepo.save(game);
                        }
                        return endGame(game, users);
                    } else {
                        return game;
                    }
                }
            }
        } else {
            return endGame(game, users);
        }
        return game;
    }

    private Game endGame(Game game, List<User> users) { //todo: rank game cards and add winnings
        Deck deck = new Deck();
        game.setRound(0);
        game.setPot(0);
        for (User u : deck.Deal(users)) {
            u.setHasPlayed(false);
            u.setBet(0);
            u.setFolded(false);
            u.setPosition(u.getPosition() + 1 < users.size() ? u.getPosition() + 1 : 0); //change who goes first
            _userRepo.save(u);//todo: add blinds
        }
        return _gameRepo.save(deck.DealPublicCards(game));
    }

    @Override
    public Game save(Game game) { //add validation
        return _gameRepo.save(game);
    }

    @Override
    public List<Game> findAll() {
        return _gameRepo.findAll();
    }

    @Override
    public Game findById(String id) {
        return _gameRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        _gameRepo.deleteById(id);
    }

    private class Deck {
        private ArrayList<Integer> _cards;
        private final Random _rand;

        public Deck() {
            _rand = new Random();
            _rand.setSeed(System.nanoTime());
        }

        public Deck(int[] publicCards, List<User> users) {
            _cards = new ArrayList<>();
            _rand = new Random();
            _rand.setSeed(System.nanoTime());
            for (int i = 0; i < 52; i++) {
                _cards.add(i);
            }
            for (User u : users) {
                _cards.remove((Integer) u.getCard1());
                _cards.remove((Integer) u.getCard2());
            }
            for (int n : publicCards) {
                _cards.remove((Integer) n);
            }
        }

        public List<User> Deal(List<User> users) {
            _cards = new ArrayList<>();
            for (int i = 0; i < 52; i++) {
                _cards.add(i);
            }
            for (User u : users) {
                u.setCard1(_cards.remove(_rand.nextInt(_cards.size())));
                u.setCard2(_cards.remove(_rand.nextInt(_cards.size())));
            }
            return users;
        }

        public Game DealPublicCards(Game game) {
            game.setPublic_card1(_cards.remove(_rand.nextInt(_cards.size())));
            game.setPublic_card2(_cards.remove(_rand.nextInt(_cards.size())));
            game.setPublic_card3(_cards.remove(_rand.nextInt(_cards.size())));
            game.setPublic_card4(_cards.remove(_rand.nextInt(_cards.size())));
            game.setPublic_card5(_cards.remove(_rand.nextInt(_cards.size())));
            return game;
        }

    }
}
