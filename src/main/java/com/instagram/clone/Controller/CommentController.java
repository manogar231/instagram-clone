package com.instagram.clone.Controller;

import com.instagram.clone.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
}
