package com.instagram.clone.Controller;

import com.instagram.clone.Repository.LikeRepository;
import com.instagram.clone.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/{id}")
    public Object getAllLikesBasedOnUser(@RequestParam("user-id") Long id){
     return likeService.getAllLikesBasedOnUser(id);
    }
}
