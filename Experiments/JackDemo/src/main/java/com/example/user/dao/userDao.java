package com.example.user.dao;

import com.example.user.model.userModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("userDao")
public class userDao implements IUserDao {

    private static List<userModel> _db = new ArrayList<>();

    @Override
    public int insertUser(UUID id, userModel user) {
        _db.add(new userModel(id, user.get_name()));
        return 1;
    }

    @Override
    public List<userModel> selectAll() {
        return _db;
    }

    @Override
    public int deleteUserById(UUID id) {
        Optional<userModel> user = selectUserById(id);
        if(user.isEmpty()) {
            return 0;
        }
        _db.remove(user.get());
        return 1;
    }

    @Override
    public int updateUserById(UUID id, userModel user) {
        return selectUserById(id).map(x -> {
            int indexToUpdate = _db.indexOf(x);
            if(indexToUpdate >= 0) {
                _db.set(indexToUpdate, new userModel(id, user.get_name()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

    @Override
    public Optional<userModel> selectUserById(UUID id) {
        return _db.stream().filter(x -> x.get_id().equals(id)).findFirst();
    }
}
