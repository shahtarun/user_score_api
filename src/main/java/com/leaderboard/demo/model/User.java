package com.leaderboard.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String username;
    private int score;
    private Set<String> badges;

    // No-argument constructor (required by Spring Data)
    public User() {
    }

    // Constructor with all fields
    public User(String userId, String username, int score, Set<String> badges) {
        this.userId = userId;
        this.username = username;
        this.score = score;
        this.badges = badges;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }
}
