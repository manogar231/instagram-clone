package com.instagram.clone.Dto;

import com.instagram.clone.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    public String Username;
    public String imageUrl;
    public String caption;
}
