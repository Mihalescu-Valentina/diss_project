package com.example.diss.controller;


import com.example.diss.dto.RegisterRequest;
import com.example.diss.model.User;
import com.example.diss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    private ResponseEntity<String> ok;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        User user = userService.getUserByName(name);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        String result = userService.removeUserById(id);
        if (result.equals("User not found.")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteUserByName(@PathVariable String name) {
        String result = userService.removeUserByName(name);
        if (result.equals("User not found.")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        String result = userService.editUser(id, request);
        if (result.equals("User not found.") || result.startsWith("Invalid")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }


}