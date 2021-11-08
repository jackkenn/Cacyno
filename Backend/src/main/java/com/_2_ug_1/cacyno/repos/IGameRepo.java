package com._2_ug_1.cacyno.repos;

import com._2_ug_1.cacyno.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface IGameRepo extends JpaRepository<Game, String> {
    @Async
    @Query(value = "SELECT * FROM game WHERE id = ?1", nativeQuery = true)
    Future<Game> AsyncGetById(String id);
}