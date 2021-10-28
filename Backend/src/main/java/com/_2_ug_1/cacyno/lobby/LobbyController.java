package com._2_ug_1.cacyno.lobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/lobby")
@RestController
public class LobbyController {
    @Autowired
    ILobbyRepo _lobbyRepo;

    @Autowired
    public LobbyController(ILobbyRepo _lobbyRepo) {
        this._lobbyRepo = _lobbyRepo;
    }

    @PostMapping
    public Lobby saveLobby(@RequestBody Lobby lobby) {
        return _lobbyRepo.save(lobby);
    }

    @GetMapping
    public List<Lobby> getAll() {
        return _lobbyRepo.findAll();
    }

    @GetMapping(path = "{id}")
    public Lobby getLobbyById(@PathVariable("id") String id) {
        return _lobbyRepo.findById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteLobby(@PathVariable("id") String id) {
        _lobbyRepo.deleteById(id);
    }

    @PutMapping
    public Lobby updateLobby(@RequestBody Lobby lobby) {
        return _lobbyRepo.save(lobby);
    }
}