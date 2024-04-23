package com.instagram.clone.Service;

import com.instagram.clone.Dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    String createUser(UserDto userDto);

    UserDto getUserProfile(Long id) throws Exception;

    String deleteUserProfile(Long id) throws Exception;

    String updateUserProfile(UserDto userDto,Long id) throws Exception;

    String followUser(long id, long targetUserId);

    String unfollowUser(long id, long targetUserId);

    String followBack(Long userId, Long followerUserId);
}
