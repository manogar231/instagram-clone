package com.instagram.clone.Controller;

import com.instagram.clone.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;
}
