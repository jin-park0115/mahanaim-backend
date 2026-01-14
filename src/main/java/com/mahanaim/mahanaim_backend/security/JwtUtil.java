package com.mahanaim.mahanaim_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret.key:thisIsMahanaimBackendProjectSecretKeyForJWTAuth256BitsLongerThan32Bytes}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 2. 만료 시간 설정 (1시간)
    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @PostConstruct
    public void init() {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 3. 토큰 생성
    public String createToken(Long userId, String email, String role) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 유저 ID 저장
                .claim("email", email)             // 이메일 저장
                .claim("role", role)               // 권한 저장
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                .setIssuedAt(date)                 // 발급 시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘 및 키
                .compact();
    }

    // 4. 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT 토큰 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 5. 토큰에서 유저 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}