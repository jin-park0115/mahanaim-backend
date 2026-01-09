package com.mahanaim.mahanaim_backend.controller;

import com.mahanaim.mahanaim_backend.dto.UserRequestDto;
import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRequestDto dto){
        try{
            userService.join(dto);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findOne(userId));
    }
}
