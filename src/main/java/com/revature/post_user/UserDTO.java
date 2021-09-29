package com.revature.post_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String id;
    private String username;
}
