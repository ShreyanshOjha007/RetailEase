package com.shreyansh.BillingSoftware.service.impl;

import com.shreyansh.BillingSoftware.entity.UserEntity;
import com.shreyansh.BillingSoftware.io.UserRequest;
import com.shreyansh.BillingSoftware.io.UserResponse;
import com.shreyansh.BillingSoftware.repository.UserRepo;
import com.shreyansh.BillingSoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserEntity newUser = convertToEntity(userRequest);
        newUser = userRepo.save(newUser);
        return convertToResponse(newUser);
    }

    @Override
    public String getUserRole(String email) {
        UserEntity existingUser = userRepo.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException("User doesn't exist with email" + email));
        return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepo.findAll().stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with userId "+ userId + " not found"));
        userRepo.delete(user);
    }

    private UserEntity convertToEntity(UserRequest userRequest) {
        return  UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .role(userRequest.getRole().toUpperCase())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }

    private UserResponse convertToResponse(UserEntity newUser) {
        return UserResponse.builder()
                .userId(newUser.getUserId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }
}
