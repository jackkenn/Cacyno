package com._2_ug_1.cacyno.services;

import com._2_ug_1.cacyno.models.Game;

import java.util.List;

public interface IGameService {
    Game save(Game game);
    List<Game> findAll();
    Game findById(String id);
    void deleteById(String id);
    Game gameInit(String gameId);
    Game play(String gameId, String userId, int bet);
}
