package com._2_ug_1.cacyno.user;

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

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return _userRepo.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return _userRepo.findAll();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") String id) {
        return _userRepo.findById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") String id) {
        _userRepo.deleteById(id);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return _userRepo.save(user);
    }
}
