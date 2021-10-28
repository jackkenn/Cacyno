package com._2_ug_1.cacyno.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/game")
@RestController
public class GameController {
    @Autowired
    IGameService _gameService;

    @Autowired
    public GameController(IGameService _gameService) {
        this._gameService = _gameService;
    }

    @PostMapping//debug only
    public Game saveGame(@RequestBody Game game) {
        return _gameService.save(game);
    }

    @GetMapping//debug only, use lobby calls
    public List<Game> getAll() {
        return _gameService.findAll();
    }

    @DeleteMapping(path = "{id}") //debug only
    public void deleteGame(@PathVariable("id") String id) {
        _gameService.deleteById(id);
    }

    @PutMapping //debug only
    public Game updateGame(@RequestBody Game game) {
        return _gameService.save(game);
    }

    @GetMapping(path = "{id}")
    public Game getGameById(@PathVariable("id") String id) {
        return _gameService.findById(id);
    }

    @GetMapping(path = "init/{id}")
    public Game initGameById(@PathVariable("id") String id) {
        return _gameService.gameInit(id);
    }

    @GetMapping(path = "play/{gameId}/{userId}/{bet}") //bet -1 to fold, TODO: edge check
    public Game userPlay(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId, @PathVariable("bet") int bet) {
        return _gameService.play(gameId, userId, bet);
    }

}
