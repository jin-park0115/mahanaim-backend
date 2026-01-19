package com.mahanaim.mahanaim_backend.dto;

import com.mahanaim.mahanaim_backend.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String role;
    private Integer age;
    private Double height;
    private String position;
    private String phoneNumber;

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole().name();
        this.age =user.getAge();
        this.height = user.getHeight();
        this.position = user.getPosition();
        this.phoneNumber = user.getPhoneNumber();
    }
}
