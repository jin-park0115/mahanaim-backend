package com.mahanaim.mahanaim_backend.dto;

import com.mahanaim.mahanaim_backend.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String role;

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole().name();
    }
}
