package com._2_ug_1.cacyno.controllers;

import com._2_ug_1.cacyno.models.Lobby;
import com._2_ug_1.cacyno.repos.ILobbyRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/lobby")
@RestController
public class LobbyController {
    @Autowired
    ILobbyRepo _lobbyRepo;

    @Autowired
    public LobbyController(ILobbyRepo _lobbyRepo) {
        this._lobbyRepo = _lobbyRepo;
    }

    @ApiOperation(value = "saveLobby", notes = "Adds a lobby to the database")
    @ApiParam(name = "Lobby", value = "the lobby to be added to the database")
    @PostMapping
    public Lobby saveLobby(@RequestBody Lobby lobby) {
        return _lobbyRepo.save(lobby);
    }

    @ApiOperation(value = "getAll", notes = "Returns all lobbies in the database")
    @GetMapping
    public List<Lobby> getAll() {
        return _lobbyRepo.findAll();
    }

    @ApiOperation(value = "getLobbyById", notes = "Returns the lobby with and id equal to id. Returns null if no lobby" +
            " is found")
    @ApiParam(name = "id", value = "the id of the lobby to be returned")
    @GetMapping(path = "{id}")
    public Lobby getLobbyById(@PathVariable("id") String id) {
        return _lobbyRepo.findById(id)
                .orElse(null);
    }

    @ApiOperation(value = "deleteLobby", notes = "Removes the lobby with an id equal to id from the database")
    @ApiParam(name = "id", value = "the id of the lobby to be removed")
    @DeleteMapping(path = "{id}")
    public void deleteLobby(@PathVariable("id") String id) {
        _lobbyRepo.deleteById(id);
    }

    @ApiOperation(value = "updateLobby", notes = "Replaces the lobby with an id equal to the given lobbies id with the" +
            "given lobby. Returns the updated lobby or null if no lobby could be found")
    @ApiParam(name = "lobby", value = "the lobby that will update the old one with matching id")
    @PutMapping
    public Lobby updateLobby(@RequestBody Lobby lobby) {
        return _lobbyRepo.save(lobby);
    }
}