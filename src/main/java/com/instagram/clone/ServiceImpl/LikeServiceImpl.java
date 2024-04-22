package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Repository.LikeRepository;
import com.instagram.clone.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

}
