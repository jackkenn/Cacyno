package com._2_ug_1.cacyno.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, String> {
    List<User> getAllByGameId(String gameId);
}
