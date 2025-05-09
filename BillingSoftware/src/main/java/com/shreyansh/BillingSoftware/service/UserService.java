package com.shreyansh.BillingSoftware.service;

import com.shreyansh.BillingSoftware.io.UserRequest;
import com.shreyansh.BillingSoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String userId);
}
