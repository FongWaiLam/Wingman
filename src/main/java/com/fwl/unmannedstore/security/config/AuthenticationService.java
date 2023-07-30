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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
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

//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
    }

    // AuthenticateManager has a method to authenticate the user
    // If the username or password is incorrect, an exception will be thrown.
    public Authentication authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        return authentication;

//        // If not exception is thrown, the user is authenticated (username and password are correct)
//        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//
//        // After getting the user, generate the jwt token
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();

    }
}
