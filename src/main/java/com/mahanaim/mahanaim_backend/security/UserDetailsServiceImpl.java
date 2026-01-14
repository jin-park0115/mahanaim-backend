package com.mahanaim.mahanaim_backend.security;

import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // 이 어노테이션을 붙이면 오타가 났을 때 바로 알려줍니다!
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + userId));

        // UserDetailsImpl(우리가 만든 포장지)에 담아서 리턴
        return new UserDetailsImpl(user);
    }
}