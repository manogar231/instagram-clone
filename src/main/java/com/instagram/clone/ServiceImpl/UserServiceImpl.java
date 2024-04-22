package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Enum.Status;
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
    public UserDto getUserProfile(Long id) throws Exception {
       Optional<User> optionalUser = userRepository.findById(id);

       if (optionalUser.isEmpty()){
          throw new Exception("User Not Found!!");
       }
        return mapper.map(optionalUser.get(),UserDto.class);
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
}
