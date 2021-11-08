package com._2_ug_1.cacyno.controllers;

import com._2_ug_1.cacyno.models.User;
import com._2_ug_1.cacyno.repos.IUserRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    IUserRepo _userRepo;

    @Autowired
    public UserController(IUserRepo _userRepo) {
        this._userRepo = _userRepo;
    }

    @ApiOperation(value = "saveUser", notes = "Adds a user to the database")
    @ApiParam(name = "User", value = "the user to be added to the database")
    @PostMapping
    public User saveUser(@RequestBody User user) {
        return _userRepo.save(user);
    }

    @ApiOperation(value = "getAll", notes = "Returns all users in the database")
    @GetMapping
    public List<User> getAll() {
        return _userRepo.findAll();
    }

    @ApiOperation(value = "getUserById", notes = "Returns the user with and id equal to id. Returns null if no user" +
            " is found")
    @ApiParam(name = "id", value = "the id of the user to be returned")
    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") String id) {
        return _userRepo.findById(id)
                .orElse(null);
    }

    @ApiOperation(value = "deleteUser", notes = "Removes the user with an id equal to id from the database")
    @ApiParam(name = "id", value = "the id of the user to be removed")
    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") String id) {
        _userRepo.deleteById(id);
    }

    @ApiOperation(value = "updateUser", notes = "Replaces the user with an id equal to the given users id with the" +
            "given user. Returns the updated user or null if no user could be found")
    @ApiParam(name = "user", value = "the user that will update the old one with matching id")
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return _userRepo.save(user);
    }
}
