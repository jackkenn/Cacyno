package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.user.IUserRepo;
import com._2_ug_1.cacyno.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public Game GameInit(String gameId) {
        Game game = null;
        List<User> users = null;
        try {
            game = _gameRepo.AsyncGetById(gameId).get();
            users = _userRepo.getAllByGameId(gameId).get();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        if(game == null || users == null) {
            return null; //handle this better
        }
        Deck deck = new Deck();
        game.setRound(0);
        for (User u : deck.Deal(users)) {
            u.setHasPlayed(false);
            u.setFolded(false);
            _userRepo.save(u);
        }
        return _gameRepo.saveAndFlush(deck.DealPublicCards(game));
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
        private Random _rand;

        public Deck() {
            _rand = new Random();
            _rand.setSeed(System.currentTimeMillis());
        }

        public Deck(int[] publicCards, List<User> users) {
            _cards = new ArrayList<>();
            _rand = new Random();
            _rand.setSeed(System.currentTimeMillis());
            for (int i = 0; i < 52; i++) {
                _cards.add(i);
            }
            for (User u : users) {
                _cards.remove(_cards.indexOf(u.getCard1()));
                _cards.remove(_cards.indexOf(u.getCard2()));
            }
            for (int n : publicCards) {
                _cards.remove(_cards.indexOf(n));
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
