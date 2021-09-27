package com._2_ug_1.cacyno.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    IUserRepo _userRepo;
    @Autowired
    public UserController(IUserRepo _userRepo) {
        this._userRepo = _userRepo;
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        _userRepo.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return _userRepo.findAll();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return _userRepo.findById(id)
                .orElse(null);
    }

    @DeleteMapping(path="{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        _userRepo.deleteById(id);
    }

    @PutMapping(path="{id}")
    public void updateUser(@RequestBody User user) {
        _userRepo.save(user);
    }
}
