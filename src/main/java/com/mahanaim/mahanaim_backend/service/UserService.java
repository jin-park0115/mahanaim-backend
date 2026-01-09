package com.mahanaim.mahanaim_backend.service;

import com.mahanaim.mahanaim_backend.dto.LoginRequestDto;
import com.mahanaim.mahanaim_backend.dto.UserRequestDto;
import com.mahanaim.mahanaim_backend.entity.Role;
import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserRequestDto dto){
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        });

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .age(dto.getAge())
                .position(dto.getPosition())
                .role(Role.USER)
                .build();
        return userRepository.save(user).getUserId();
    }

    public User login(LoginRequestDto dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    public User findOne(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));
    }
}
