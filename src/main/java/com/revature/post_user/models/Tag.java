package com.revature.post_user.models;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@DynamoDbBean
public class Tag {

    private String tagName;
    private String tagColor;

}
