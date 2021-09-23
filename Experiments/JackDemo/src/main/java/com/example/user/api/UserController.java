package com.example.user.api;

import com.example.user.model.userModel;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService _userService;

    @Autowired
    public UserController(UserService _userService) {
        this._userService = _userService;
    }

    @PostMapping
    public void addUser(@RequestBody userModel user) {
        _userService.addUser(user);
    }

    @GetMapping
    public List<userModel> getAll() {
        return _userService.getAll();
    }

    @GetMapping(path = "{id}")
    public userModel getUserById(@PathVariable("id") UUID id) {
        return _userService.getUserById(id)
                .orElse(null);
    }

    @DeleteMapping(path="{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        _userService.deleteUser(id);
    }

    @PutMapping(path="{id}")
    public void updateUser(@PathVariable("id") UUID id, @RequestBody userModel user) {
        _userService.updateUser(id, user);
    }
}
