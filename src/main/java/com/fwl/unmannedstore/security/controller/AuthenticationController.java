package com.fwl.unmannedstore.security.controller;

import com.fwl.unmannedstore.security.config.JwtService;
import com.fwl.unmannedstore.security.authentication.Message;
import com.fwl.unmannedstore.security.entity.User;
import com.fwl.unmannedstore.security.authentication.UserInformation;
import com.fwl.unmannedstore.security.authentication.AuthenticationRequest;
import com.fwl.unmannedstore.security.authentication.AuthenticationService;
import com.fwl.unmannedstore.security.authentication.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    // Endpoints to create a new acc and authenticate an existing user

    // Delegate the register and authenticate to a service
    private final AuthenticationService authService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid
        @RequestBody RegisterRequest request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    // RegisterRequest holds the registration information
    @PostMapping("/signin")
    public String signIn(@Valid
            @RequestBody AuthenticationRequest request
    ) {
        log.info("authenticate entered");

        Authentication authentication = authService.authenticate(request);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);

        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .body(new UserInformation(user.getId(),
//                        user.getUsername(),
//                        user.getEmail(),
//                        user.getRole()));

        return "redirect:/usms";
    }

    @PostMapping("/signout")
    public ResponseEntity<Message> signOut() {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new Message("You've been signed out!"));
    }

}
