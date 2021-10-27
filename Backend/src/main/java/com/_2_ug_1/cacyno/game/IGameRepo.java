package com._2_ug_1.cacyno.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IGameRepo extends JpaRepository<Game, String>{
}