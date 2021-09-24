package com.example.user.service;

import com.example.user.dao.IUserDao;
import com.example.user.model.userModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final IUserDao _userDao;

    @Autowired //inject
    public UserService(@Qualifier("userDao") IUserDao _userDao) {
        this._userDao = _userDao;
    }

    public int addUser(userModel user) {
        return _userDao.insertUser(user);
    }

    public List<userModel> getAll() {
        return _userDao.selectAll();
    }

    public Optional<userModel> getUserById(UUID id) {
        return _userDao.selectUserById(id);
    }

    public int deleteUser(UUID id) {
        return _userDao.deleteUserById(id);
    }

    public int updateUser(UUID id, userModel user) {
        return _userDao.updateUserById(id, user);
    }
}
