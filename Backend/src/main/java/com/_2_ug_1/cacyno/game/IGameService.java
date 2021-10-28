package com._2_ug_1.cacyno.game;

import org.springframework.stereotype.Component;

import java.util.List;

public interface IGameService {
    Game save(Game game);
    List<Game> findAll();
    Game findById(String id);
    void deleteById(String id);
    Game GameInit(String gameId);
}
