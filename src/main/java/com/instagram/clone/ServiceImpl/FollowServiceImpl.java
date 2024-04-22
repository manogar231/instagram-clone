package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Repository.FollowRepository;
import com.instagram.clone.Service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;
}
