package com.instagram.clone.Controller;

import com.instagram.clone.Dto.UserDto;
import com.instagram.clone.Entity.UserRequest;
import com.instagram.clone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserDto getUserProfileById(@PathVariable Long id) throws Exception {
        return userService.getUserProfileById(id);
    }

    @GetMapping("/alluser")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public Object updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        return userService.updateUserById(id, userDto);
    }

    @PutMapping("/status/{id}")
    public Object statusUpdateForUser(@PathVariable(value = "id") Long id) throws Exception {
        return userService.statusUpdate(id);
    }

    @DeleteMapping("/users/{id}")
    public Object deleteUserById(@PathVariable("id") Long id) throws Exception{
        return userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public Object userLogin(@RequestBody UserRequest userRequest, String usernameFromJwt) throws Exception{
        return userService.loginUser(userRequest, usernameFromJwt);
    }



}
