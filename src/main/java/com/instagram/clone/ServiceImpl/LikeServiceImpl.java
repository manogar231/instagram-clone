package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Entity.Like;
import com.instagram.clone.Repository.LikeRepository;
import com.instagram.clone.Repository.UserRepository;
import com.instagram.clone.Service.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Object getAllLikesBasedOnUser(Long id) {

        List<Like> likes =  likeRepository.findAll();

        if (likes.isEmpty()) {
            return "Empty !!";
        }
       return likes.stream()
               .filter(like -> like.getUser().getId().equals(id))
               .collect(Collectors.toList());
    }
}
