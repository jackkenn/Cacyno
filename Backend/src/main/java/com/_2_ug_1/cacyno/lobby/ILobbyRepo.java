package com._2_ug_1.cacyno.lobby;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ILobbyRepo extends JpaRepository<Lobby, String> {
}