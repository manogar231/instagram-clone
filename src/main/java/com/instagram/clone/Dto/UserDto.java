package com.instagram.clone.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public String userName;
    public String Email;
    public String Password;
    public String profilePictureUrl;
    public String Bio;

}
