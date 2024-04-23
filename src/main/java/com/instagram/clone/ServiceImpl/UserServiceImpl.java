package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Auth.JwtTokenUtil;
import com.instagram.clone.Config.EmailSender;
import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Entity.UserRequest;
import com.instagram.clone.Enum.Status;
import com.instagram.clone.Repository.UserRepository;
import com.instagram.clone.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    EmailSender emailSender;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JavaMailSender javaMailSender;

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

    @Override
    public Object loginUser(UserRequest userRequest, String usernameFromJwt) throws Exception {
        Optional<User> user = userRepository.userByEmail(usernameFromJwt);
        if (user.isEmpty()) {
            throw new Exception("Invalid username");
        }
        String encodedPassword = Base64.getEncoder().encodeToString(userRequest.getPassword().getBytes());
        User commonUser = user.get();
        if (commonUser != null) {
            if (commonUser.getPassword().equals(encodedPassword)) {
                String token = JwtTokenUtil.generateToken(userRequest.getToken());
                return new UserRequest(token);
            }
        }
        return "User Login Successful";
    }

    @Override
    public Object forgetPassword(String email) throws Exception {
        Random random = new Random(1000);
        Optional<User> optionalUser = userRepository.findCommonUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found with this Email Id");
        }
        User commonUser = optionalUser.get();
        int otp = random.nextInt(999999);
        emailSender.sendNoReplyEmail(javaMailSender, email, "Otp verification", "your Otp is" + otp);
        commonUser.setOneTimePassword(String.valueOf(otp));
        userRepository.save(commonUser);
        return "Password created";
    }

    @Override
    public Object changePasswordWithOtp(String encodedOtp, UserRequest request) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedOtp);
        String decodedValue = new String(decodedBytes);
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new Exception("The new password and confirm password does not match" + request);
        }
        Optional<User> user = userRepository.findCommonUserByOneTimePassword(decodedValue);
        if (user.isEmpty()) {
            throw new Exception("Invalid OTP");
        }
        User commonUser = user.get();
        String encodedPassword = Base64.getEncoder().encodeToString(request.getNewPassword().getBytes());
        commonUser.setPassword(encodedPassword);
        userRepository.save(commonUser);
        return "Password changed";
    }

    @Override
    public Object changePasswordWithOldPassword(UserRequest request, String usernameFromJwt) throws Exception {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new Exception("The new password and confirm password does not match" + request);
        }
        Optional<User> user = userRepository.userByEmail(usernameFromJwt);
        if (user.isEmpty()) {
            throw new Exception("Invalid Username");
        }
        String encodedPassword = Base64.getEncoder().encodeToString(request.getNewPassword().getBytes());
        User commonUser = user.get();
        commonUser.setPassword(encodedPassword);
        userRepository.save(commonUser);
        return "Password changed";
    }

}
