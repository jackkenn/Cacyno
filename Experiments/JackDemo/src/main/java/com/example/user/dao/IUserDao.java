package com.example.user.dao;

import com.example.user.model.userModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserDao {
    int insertUser(UUID id, userModel user);

    default int insertUser(userModel user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<userModel> selectAll();

    Optional<userModel> selectUserById(UUID id);

    int deleteUserById(UUID id);

    int updateUserById(UUID id, userModel model);
}
