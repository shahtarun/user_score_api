package com.leaderboard.demo;

import com.leaderboard.demo.model.User;
import com.leaderboard.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

//    @PutMapping("/{userId}")
//    public User updateUserScore(@PathVariable String userId, @RequestParam int score) {
//        return userService.updateUserScore(userId, score);
//    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserScore(@PathVariable String userId,@RequestBody Map<String, Integer> requestBody) {
        int newScore = requestBody.get("score");
        User updatedUser = userService.updateUserScore(userId, newScore);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

