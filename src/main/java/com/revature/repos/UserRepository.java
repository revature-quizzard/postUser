package com.revature.repos;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.revature.models.User;
import lombok.SneakyThrows;

public class UserRepository {

    private static final UserRepository userRepository = new UserRepository();
    private final DynamoDBMapper dbReader;

    public UserRepository() {
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public static UserRepository getInstance() {
        return userRepository;
    }

    @SneakyThrows
    public User saveUser(User user) {
        dbReader.save(user);
        return user;
    }
}
