package com._2_ug_1.cacyno.repos;

import com._2_ug_1.cacyno.models.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILobbyRepo extends JpaRepository<Lobby, String> {
}