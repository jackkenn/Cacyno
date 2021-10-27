package com._2_ug_1.cacyno.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/game")
@RestController
public class GameController {
    @Autowired
    IGameRepo _gameRepo;

    @Autowired
    public GameController(IGameRepo _gameRepo){this._gameRepo = _gameRepo;}

    @PostMapping
    public Game saveGame(@RequestBody Game game){return _gameRepo.save(game);}

    @GetMapping
    public List<Game> getAll(){return _gameRepo.findAll();}

    @GetMapping(path = "{id}")
    public Game getGameById(@PathVariable("id") String id){
        return _gameRepo.findById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteGame(@PathVariable("id") String id){_gameRepo.deleteById(id);}

    @PutMapping
    public Game updateGame(@RequestBody Game game){return _gameRepo.save(game);}

}
