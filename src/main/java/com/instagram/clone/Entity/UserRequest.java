package com.instagram.clone.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String newPassword;

    private String confirmPassword;

    private String oldPassword;

    private String password;

    private String token;

    public UserRequest(String token) {
    }

}
