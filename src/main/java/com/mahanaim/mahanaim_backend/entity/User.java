package com.mahanaim.mahanaim_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String name;
    private Integer age;
    private String position;

    @Enumerated(EnumType.STRING)
    private Role role;

}
