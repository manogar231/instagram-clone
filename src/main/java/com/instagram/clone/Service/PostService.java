package com.instagram.clone.Service;

import com.instagram.clone.Dto.PostDto;
import com.instagram.clone.Entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostService {
    Object doPost(PostDto postDto);

    List<Post> getAllPost();
}
