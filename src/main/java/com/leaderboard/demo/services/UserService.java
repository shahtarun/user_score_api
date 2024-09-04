package com.leaderboard.demo.services;

import com.leaderboard.demo.model.User;
import com.leaderboard.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        if(userRepository.count()==1)
            return new ArrayList<>(userRepository.findAll());
        return userRepository.findAll().stream()
                .sorted((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()))
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User registerUser(User user) {
        user.setScore(0);
        user.setBadges(new HashSet<>());
        return userRepository.save(user);
    }

    public User updateUserScore(String userId, int newScore) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setScore(newScore);
        updateBadges(user);
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private void updateBadges(User user) {
        int score = user.getScore();
        Set<String> badges = user.getBadges();
        if (score >= 60) {
            badges.add("Code Master");
        } else if (score >= 30) {
            badges.add("Code Champ");
        } else if (score >= 1) {
            badges.add("Code Ninja");
        }
    }
}

