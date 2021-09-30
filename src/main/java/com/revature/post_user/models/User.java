package com.revature.post_user.models;

import com.revature.post_user.dtos.UserDTO;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@DynamoDbBean
public class User {

    private String id;
    private String username;
    private List<SetDocument> favorite_sets;
    private List<SetDocument> created_sets;
    private String profile_picture;
    private int points;
    private int wins;
    private int losses;
    private String registration_date;
    private List<String> gameRecords;

    public User() {
        super();
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.favorite_sets = new ArrayList<>();
        this.created_sets = new ArrayList<>();
        this.profile_picture = "";
        this.points = 0;
        this.wins = 0;
        this.losses = 0;
        this.registration_date = LocalDateTime.now().toString();
        this.gameRecords = new ArrayList<>();
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
