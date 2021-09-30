package com.revature.post_user.dtos;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@Builder
public class UserDTO {
    private String id;
    private String username;

    /**
     * Basic validation to check that any of the UserDTO fields are not
     * null or empty string
     *
     * @return
     */
    public boolean isValid() {
        if (id == null || id.trim().equals("")) return false;
        return (username != null && !username.trim().equals(""));
    }
}
