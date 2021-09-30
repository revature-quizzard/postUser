package com.revature.post_user.models;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;

@Data
@DynamoDbBean
public class SetDocument {

    private String id;
    private String set_name;
    private List<Tag> tags;
    private String author;
    private boolean is_public;
    private int views;
    private int plays;
    private int studies;
    private int favorites;

}
