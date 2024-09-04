package com.leaderboard.demo.repositories;

import com.leaderboard.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

