package com.instagram.clone.Controller;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String CreateUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping("/get-my-profile/{id}")
    public UserDto getUserProfile(@PathVariable Long id) throws Exception {
        return userService.getUserProfile(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserProfile(@PathVariable Long id) throws Exception {
        return userService.deleteUserProfile(id);
    }

    @PutMapping("/update/profile/{id}")
    public String UpdateUserProfile(@PathVariable Long id ,@RequestBody UserDto userDto) throws Exception {
        return userService.updateUserProfile(userDto,id);
    }
}
