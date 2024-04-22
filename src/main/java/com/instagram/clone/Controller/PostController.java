package com.instagram.clone.Controller;

import com.instagram.clone.Dto.PostDto;
import com.instagram.clone.Entity.Post;
import com.instagram.clone.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/do-post")
    public Object doPost(@RequestBody PostDto postDto){
      return postService.doPost(postDto);
    }

    @GetMapping("/getAll")
    public List<Post> getAllPost(){
        return postService.getAllPost();
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId, @RequestParam Long userId){
        return postService.likePost(postId, userId);
    }

    @PostMapping("/{postId}/comment")
    public String commentOnPost(@PathVariable Long postId, @RequestParam Long userId, @RequestParam String content) {
        return postService.commentOnPost(postId, userId, content);
    }
}
