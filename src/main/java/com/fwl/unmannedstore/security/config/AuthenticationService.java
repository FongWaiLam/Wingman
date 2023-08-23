package com.fwl.unmannedstore.security.config;

import com.fwl.unmannedstore.security.authentication.AuthenticationRequest;
import com.fwl.unmannedstore.security.authentication.JwtService;
import com.fwl.unmannedstore.security.authentication.RegisterRequest;
import com.fwl.unmannedstore.security.entity.User;
import com.fwl.unmannedstore.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return user;
    }

    // AuthenticateManager has a method to authenticate the user
    // If the username or password is incorrect, an exception will be thrown.
    public Authentication authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()));
            return authentication;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Save user
    public void save(User user) {
        userRepository.save(user);
    }

}
