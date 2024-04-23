package com.instagram.clone.Service;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Entity.UserRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    String createUser(UserDto userDto);

    UserDto getUserProfileById(Long id) throws Exception;

    List<UserDto> getAllUsers();

    public User updateUserById(Long id, UserDto userDTO) throws Exception;

    Object statusUpdate(Long id) throws Exception;

    Object deleteUserById(Long id) throws Exception;

    Object loginUser(UserRequest userRequest, String usernameFromJwt) throws Exception;

    Object forgetPassword(String email) throws Exception;

    Object changePasswordWithOtp(String encodedOtp, UserRequest request) throws Exception;

    Object changePasswordWithOldPassword(UserRequest request, String usernameFromJwt) throws Exception;
}
