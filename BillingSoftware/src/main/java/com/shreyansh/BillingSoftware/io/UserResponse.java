package com.shreyansh.BillingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userId;

    private String email;

    private String role;

    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
