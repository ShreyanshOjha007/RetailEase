package com.shreyansh.BillingSoftware.repository;


import com.shreyansh.BillingSoftware.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {


    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(String userId);
}
