package com._2_ug_1.cacyno.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/players")
@RestController
public class PlayersController {
    @Autowired
    IPlayersRepo _playersRepo;

    @Autowired
    public PlayersController(IPlayersRepo _playersRepo){this._playersRepo = _playersRepo;}

    @PostMapping
    public Players savePlayers(@RequestBody Players players){return _playersRepo.save(players);}

    @GetMapping
    public List<Players> getAll(){return _playersRepo.findAll();}

    @GetMapping(path = "{user_id}")
    public Players getPlayersById(@PathVariable("user_id") String user_id){
        return _playersRepo.findById(user_id)
                .orElse(null);
    }

    @DeleteMapping
    public void deletePlayer(@PathVariable("user_id") String user_id){_playersRepo.deleteById(user_id);}

    @PutMapping
    public Players updatePlayers(@RequestBody Players players){return _playersRepo.save(players);}

}
