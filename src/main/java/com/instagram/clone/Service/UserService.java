package com.instagram.clone.Service;

import com.instagram.clone.Dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    String createUser(UserDto userDto);

    UserDto getUserProfile(Long id) throws Exception;
}
