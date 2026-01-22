package com.mahanaim.mahanaim_backend.controller;

import com.mahanaim.mahanaim_backend.dto.LoginRequestDto;
import com.mahanaim.mahanaim_backend.dto.UserRequestDto;
import com.mahanaim.mahanaim_backend.dto.UserResponseDto;
import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.security.UserDetailsImpl;
import com.mahanaim.mahanaim_backend.service.UserService;
import com.mahanaim.mahanaim_backend.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> join(@RequestBody UserRequestDto dto){
        try{
            userService.signup(dto);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto, HttpServletResponse response){
        try {
            User user = userService.login(dto);
            String token = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getRole().toString());

            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .path("/")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("none")
                    .maxAge(3600)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            body.put("name", user.getName());

            return ResponseEntity.ok(user.getName() + "님 환영합니다.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(500).body("로그인 중 서버 오류가 발생했습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findOne(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails == null){
            return ResponseEntity.status(401).body("로그인 정보가 없습니다.");
        }

        User user = userDetails.getUser();
        return ResponseEntity.ok(new UserResponseDto(user));
    }
    @PatchMapping("/me")
    public ResponseEntity<?> updateMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserRequestDto dto){
        Long userId = userDetails.getUser().getUserId();
        userService.updateUser(userId, dto);
        return ResponseEntity.ok("정보가 성공적으로 수정되었습니다.");
    }

}
