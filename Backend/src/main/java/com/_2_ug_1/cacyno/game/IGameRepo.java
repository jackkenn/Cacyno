package com._2_ug_1.cacyno.game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameRepo extends JpaRepository<Game, String>{
}