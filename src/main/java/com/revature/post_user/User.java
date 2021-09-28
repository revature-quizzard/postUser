package com.revature.post_user;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String id;

    @DynamoDBAttribute
    private String username;

    @DynamoDBAttribute
    private List<Object> favoriteSets;

    @DynamoDBAttribute
    private List<Object> createdSets;

    @DynamoDBAttribute
    private String profilePicture;

    @DynamoDBAttribute
    private int points;

    @DynamoDBAttribute
    private int wins;

    @DynamoDBAttribute
    private int losses;

    @DynamoDBAttribute
    private String registrationDate;

    @DynamoDBAttribute
    private List<Object> gameRecord;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.favoriteSets = new ArrayList<>();
        this.createdSets = new ArrayList<>();
        this.profilePicture = "";
        this.points = 0;
        this.wins = 0;
        this.losses = 0;
        this.registrationDate = LocalDateTime.now().toString();
        this.gameRecord = new ArrayList<>();
    }
}
