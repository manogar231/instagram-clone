package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Repository.CommentRepository;
import com.instagram.clone.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
}
