package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.Follow;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Enum.Status;
import com.instagram.clone.Repository.FollowRepository;
import com.instagram.clone.Repository.UserRepository;
import com.instagram.clone.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public String createUser(UserDto userDto) {
        try {
          Optional<User> optionalUser = userRepository.findByuserName(userDto.getUserName());
          if (optionalUser.isPresent()){
              return "User Name Already Exits!!Try Another One Please!!!";
          }
            User user = User.builder()
                    .userName(userDto.getUserName())
                    .email(userDto.getEmail())
                    .bio(userDto.getBio())
                    .password(userDto.getPassword())
                    .profilePictureUrl(userDto.getProfilePictureUrl())
                    .status(Status.ACTIVE)
                    .build();

          userRepository.save(user);
        }catch (Exception ex){

        }
        return "User Account Created Successfully !!!! ";
    }

    @Override
    public User getUserProfile(Long id) throws Exception {
       Optional<User> optionalUser = userRepository.findById(id);

       if (optionalUser.isEmpty()){
          throw new Exception("User Not Found!!");
       }
        return optionalUser.get();
    }

    @Override
    public String deleteUserProfile(Long id) throws Exception {
      Optional<User> optionalUser = userRepository.findById(id);
      if (optionalUser.isEmpty()){
          throw new Exception("user not found !!");
      }
      if (optionalUser.get().getStatus().equals(Status.DELETED)||optionalUser.get().getStatus().equals(Status.IN_ACTIVE)){
          throw new Exception("User Already Deleted or IN-Active !!");
      }
      User user = optionalUser.get();
      user.setStatus(Status.DELETED);
      userRepository.save(user);
      return "User Saved Successfully !!";
    }

    @Override
    public String updateUserProfile(UserDto userDto, Long id) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) throw new Exception("user not found !!");

        User user = optionalUser.get();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());
        userRepository.save(user);

        return "User Updated Successfully !!";
    }

    @Override
    public String followUser(long id, long targetUserId) {
        // Check if both users exist
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> targetUserOptional = userRepository.findById(targetUserId);
        if (userOptional.isEmpty() || targetUserOptional.isEmpty()) {
            return "Failed to follow user: User(s) not found.";
        }

        User user = userOptional.get();
        User targetUser = targetUserOptional.get();

        // Check if the user is already following the target user
        boolean isFollowing = user.getFollowing().stream()
                .anyMatch(follow -> follow.getFollowedUser().getId().equals(targetUserId));
        if (isFollowing) {
            return "Failed to follow user: User is already following the target user.";
        }

        // Create a new Follow entity
        Follow follow = new Follow();
        follow.setFollowerUser(user);
        follow.setFollowedUser(targetUser);

        // Add the follow relationship to the user's following list
        user.getFollowing().add(follow);

        // Add the follow relationship to the target user's followers list
        targetUser.getFollowers().add(follow);

        // Save the follow data
        followRepository.save(follow);

        // Save both users to persist the changes
        userRepository.save(user);
        userRepository.save(targetUser);

        return "User followed successfully!";
    }

    public String unfollowUser(long id, long targetUserId) {
        // Check if both users exist
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> targetUserOptional = userRepository.findById(targetUserId);
        if (userOptional.isEmpty() || targetUserOptional.isEmpty()) {
            return "Failed to unfollow user: User(s) not found.";
        }

        User user = userOptional.get();
        User targetUser = targetUserOptional.get();

        // Find the follow entity representing the follow relationship
        Optional<Follow> followOptional = user.getFollowing().stream()
                .filter(follow -> follow.getFollowedUser().getId().equals(targetUserId))
                .findFirst();

        if (followOptional.isEmpty()) {
            return "Failed to unfollow user: User is not following the target user.";
        }

        Follow follow = followOptional.get();

        // Remove the follow relationship from the user's following list
        user.getFollowing().remove(follow);

        // Remove the follow relationship from the target user's followers list
        targetUser.getFollowers().remove(follow);

        // Delete the Follow entity to remove the follow relationship
        followRepository.delete(follow);

        // Save both users to persist the changes
        userRepository.save(user);
        userRepository.save(targetUser);

        return "User unfollowed successfully!";
    }

    @Override
    public String followBack(Long userId, Long followerUserId) {
        // Check if both users exist
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followerUserOptional = userRepository.findById(followerUserId);
        if (userOptional.isEmpty() || followerUserOptional.isEmpty()) {
            return "Failed to follow back: User(s) not found.";
        }

        User user = userOptional.get();
        User followerUser = followerUserOptional.get();

        // Check if the user is already following the follower user
        boolean isFollowing = user.getFollowing().stream()
                .anyMatch(follow -> follow.getFollowedUser().getId().equals(followerUserId));
        if (isFollowing) {
            return "Failed to follow back: User is already following the follower user.";
        }

        // Create a new Follow entity for the follow-back relationship
        Follow followBack = new Follow();
        followBack.setFollowerUser(user);
        followBack.setFollowedUser(followerUser);

        // Add the follow-back relationship to the user's following list
        user.getFollowing().add(followBack);

        // Add the follow-back relationship to the follower user's followers list
        followerUser.getFollowers().add(followBack);

        //followRepository.save(followBack);
        // Save both users to persist the changes
        userRepository.save(user);
        userRepository.save(followerUser);

        return "Follow back successful!";
    }


}
