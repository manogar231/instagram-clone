package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Enum.Status;
import com.instagram.clone.Repository.UserRepository;
import com.instagram.clone.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public UserDto getUserProfileById(Long id) throws Exception {
       Optional<User> optionalUser = userRepository.findById(id);

       if (optionalUser.isEmpty()){
          throw new Exception("User Not Found!!");
       }
        return mapper.map(optionalUser.get(),UserDto.class);
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(User -> mapper.map(User, UserDto.class)).collect(Collectors.toList());
    }

    public User updateUserById(Long id, UserDto userDTO) throws Exception {
        User user1 = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        if (Objects.nonNull(userDTO.getUserName())) {
            user1.setUserName(userDTO.getUserName());
        }
        if (Objects.nonNull(userDTO.getEmail())) {
            user1.setEmail(userDTO.getEmail());
        }
        if (Objects.nonNull(userDTO.getProfilePictureUrl())) {
            user1.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        }
        if (Objects.nonNull(userDTO.getBio())) {
            user1.setBio(userDTO.getBio());
        }

        return userRepository.save(user1);
    }

    public Object statusUpdate(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Exception("User Not Found!!");
        }
        User user = optionalUser.get();
        userRepository.updateStatusForUser(id, user.getStatus());
        return "User status updated successfully";
    }

    @Override
    public Object deleteUserById(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Exception("User Not Found!!");
        }
        userRepository.deleteUser(id);
        return "User deleted successfully";
    }

}
