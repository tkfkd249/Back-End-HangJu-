package com.project.hang.service;

import java.util.Collections;
import java.util.Optional;

import com.project.hang.dto.UserDto;
import com.project.hang.entity.Authority;
import com.project.hang.entity.User;
import com.project.hang.repository.UserRepository;
import com.project.hang.util.SecurityUtil;
import javassist.bytecode.DuplicateMemberException;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserDto signup(UserDto userDto) throws DuplicateMemberException {
        if (userRepository.findOneWithAuthoritiesByUserId(userDto.getUserid()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .userId(userDto.getUserid())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String userid) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserId(userid).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUserId).orElse(null));
    }
}