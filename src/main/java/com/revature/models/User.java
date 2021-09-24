package com.revature.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBAttribute
    private String id;

    @DynamoDBAttribute
    private String username;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private List<String> favoriteSets;

    @DynamoDBAttribute
    private List<String> createdSets;

    @DynamoDBAttribute
    private String profilePicture;

    @DynamoDBAttribute
    private int points;

    @DynamoDBAttribute
    private int wins;

    @DynamoDBAttribute
    private int losses;

    @DynamoDBAttribute
    private LocalDateTime registrationDate;

    @DynamoDBAttribute
    private List<String> gameRecord;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.name = userDTO.getUsername();
        this.favoriteSets = new ArrayList<>();
        this.createdSets = new ArrayList<>();
        this.profilePicture = "";
        this.points = 0;
        this.wins = 0;
        this.losses = 0;
        this.registrationDate = LocalDateTime.now();
        this.gameRecord = new ArrayList<>();
    }
}
