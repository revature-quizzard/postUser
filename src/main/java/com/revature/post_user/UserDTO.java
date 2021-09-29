package com.revature.post_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String id;
    private String username;

    public boolean isValid() {
        if (id == null || id.trim().equals("")) return false;
        return (username != null && !username.trim().equals(""));
    }
}
