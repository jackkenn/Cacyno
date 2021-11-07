package com._2_ug_1.cacyno.controllers;

import com._2_ug_1.cacyno.models.Game;
import com._2_ug_1.cacyno.services.IGameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "saveGame", notes = "Adds a game to the database")
    @ApiParam(name = "Game", value = "the game to be added to the database")
    @PostMapping//debug only
    public Game saveGame(@RequestBody Game game) {
        return _gameService.save(game);
    }

    @ApiOperation(value = "getAll", notes = "Returns all games in the data base")
    @GetMapping//debug only, use lobby calls
    public List<Game> getAll() {
        return _gameService.findAll();
    }

    @ApiOperation(value = "deleteGame", notes = "Removes the game with an id equal to id from the data base")
    @ApiParam(name = "id", value = "the id of the game to be removed")
    @DeleteMapping(path = "{id}") //debug only
    public void deleteGame(@PathVariable("id") String id) {
        _gameService.deleteById(id);
    }

    @ApiOperation(value = "updateGame", notes = "Replaces the game with an id equal to the given games id with the" +
            "given game. Returns the updated game or null if no game could be found")
    @ApiParam(name = "game", value = "the game that will update the old one with matching id")
    @PutMapping //debug only
    public Game updateGame(@RequestBody Game game) {
        return _gameService.save(game);
    }

    @ApiOperation(value = "getGameById", notes = "Returns the game with and id equal to id. Returns null if no game" +
            " is found")
    @ApiParam(name = "id", value = "the id of the game to be returned")
    @GetMapping(path = "{id}")
    public Game getGameById(@PathVariable("id") String id) {
        return _gameService.findById(id);
    }

    @ApiOperation(value = "initGameById", notes = "Resets values for the game and users in game" +
            ", and deals cards. Returns the updated game, Returns nothing if no game is found")
    @ApiParam(name = "id", value = "the id of the game to initialized")
    @GetMapping(path = "init/{id}")
    public Game initGameById(@PathVariable("id") String id) {
        return _gameService.gameInit(id);
    }

    @ApiOperation(value = "userPlay", notes = "The user with id equal to userId will" +
            "attempt to make a play on the game with an id equal to gameId. The user will only be able to make a play" +
            " if it is their turn. At the end of the last round, when the last player ends their turn the end" +
            " game sequence begins. Will return the updated game if the user is in the game, it is their turn and" +
            " they made a valid bet, if the game is not found, user is not found, or move is invalid" +
            " then it will return null")
    @GetMapping(path = "play/{gameId}/{userId}/{bet}") //bet -1 to fold, TODO: edge check
    public Game userPlay(@PathVariable("gameId") @ApiParam(name = "gameId", value = "the game the user is attempting to play on") String gameId
            , @PathVariable("userId") @ApiParam(name = "userId", value = "the user in the game attempting to make a play") String userId
            , @PathVariable("bet") @ApiParam(name = "bet", value = "Determins the play the user will make in the game. Bet < 0: the user will fold, " +
            "Bet = 0: the user will check, Bet > 0 and valid: the user will spend some of their in game money to bet") int bet) {
        return _gameService.play(gameId, userId, bet);
    }

}
