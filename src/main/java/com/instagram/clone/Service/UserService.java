package com.instagram.clone.Service;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
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

}
