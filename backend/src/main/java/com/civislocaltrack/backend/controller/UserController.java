package com.civislocaltrack.backend.controller;

import com.civislocaltrack.backend.model.User;
import com.civislocaltrack.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        // return userService.loginUser(email, password);
        User user = userService.loginUser(email, password);
        if (user != null) {
            return "Connexion réussie !";
        } else {
            return "Email ou mot de passe incorrect.";
        }
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
