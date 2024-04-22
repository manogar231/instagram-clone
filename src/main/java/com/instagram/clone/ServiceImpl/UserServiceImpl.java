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
}
