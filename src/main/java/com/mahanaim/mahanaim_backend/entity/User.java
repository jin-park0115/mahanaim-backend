package com.mahanaim.mahanaim_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String password;
    private String name;
    private Integer age;
    private String position;
    private Double height;
    private String phoneNumber;

    // 소셜 로그인 여부를 체크하기 위한 필드
    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateProfile(Integer age, Double height, String position, String phoneNumber) {
        if (age != null) this.age = age;
        if (height != null) this.height = height;
        if (position != null) this.position = position;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
    }
}
