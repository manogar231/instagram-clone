package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Dto.PostDto;
import com.instagram.clone.Entity.Post;
import com.instagram.clone.Repository.PostRepository;
import com.instagram.clone.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;



    @Override
    public Object doPost(PostDto postDto) {

        Post post = Post.builder()
                .imageUrl(postDto.getImageUrl())
                .caption(postDto.getCaption())
                .build();

      postRepository.save(post);
       return "Post Uploaded Successfully!!" ;
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
