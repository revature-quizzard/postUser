package com.revature.post_user;

import com.revature.post_user.models.User;
import lombok.SneakyThrows;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository() {
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));
    }

    /**
     * Takes in a User object and attempts to save it to the database.
     * If no exception occurs, we return the User object.
     *
     * @param user
     * @return
     */
    @SneakyThrows
    public User saveUser(User user) {
        userTable.putItem(user);
        return user;
    }
}
