package com.leaderboard.demo;

import com.leaderboard.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl = "/users";

    @BeforeEach
    public void setup() {
        // Set up any preconditions if necessary
    }

    @Test
    public void testUserRegistration() {
        User user = new User("1", "User1",0, new HashSet<>());
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, user, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo("User1");
        assertThat(response.getBody().getScore()).isEqualTo(0);
        assertThat(response.getBody().getBadges()).isEmpty();
    }

    @Test
    public void testUserRetrieval() {
        User user = new User("2", "User2",0, new HashSet<>());
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/2", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo("User2");
    }


    @Test
    public void testUpdateUserScore() {
        // Step 1: Create a new user with an initial score of 0 and empty badges
        User user = new User("3", "User3", 0, new HashSet<>());
        restTemplate.postForEntity(baseUrl, user, User.class);

        // Step 2: Update the user's score to 45
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("score", 45);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(requestBody, headers);

        // Perform PUT request to update the user score
        restTemplate.exchange(baseUrl + "/3", HttpMethod.PUT, entity, Void.class);

        // Step 3: Retrieve the updated user and verify the changes
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/3", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        User updatedUser = response.getBody();
        assertNotNull(updatedUser);
        assertThat(updatedUser.getScore()).isEqualTo(45);

        // Check if badges have been updated correctly based on the new score
        assertThat(updatedUser.getBadges()).containsExactly("Code Champ");
    }



    @Test
    public void testDeleteUser() {
        User user = new User("4", "User4",0, new HashSet<>());
        restTemplate.postForEntity(baseUrl, user, User.class);

        restTemplate.delete(baseUrl + "/4");

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/4", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testRetrieveAllUsers() {
        User user1 = new User("5", "User5",0, new HashSet<>());
        User user2 = new User("6", "User6",0, new HashSet<>());

        restTemplate.postForEntity(baseUrl, user1, User.class);
        restTemplate.postForEntity(baseUrl, user2, User.class);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("User5", "User6");
    }
}
