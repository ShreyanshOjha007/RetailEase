package com.shreyansh.BillingSoftware.controller;


import com.shreyansh.BillingSoftware.io.UserRequest;
import com.shreyansh.BillingSoftware.io.UserResponse;
import com.shreyansh.BillingSoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest userRequest){
        try{
            return userService.createUser(userRequest);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to register user");
        }
    }

    @GetMapping("/users")
    public List<UserResponse> readUser(){
        return userService.readUsers();
    }

    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId){
        try{
            userService.deleteUser(userId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
}
