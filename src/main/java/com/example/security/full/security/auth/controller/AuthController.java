package com.example.security.full.security.auth.controller;

import com.example.security.full.security.UserSecurity.dao.JpaUserDetailsService;

import com.example.security.full.security.UserSecurity.model.UserSecurity;
import com.example.security.full.security.auth.controller.registration.RegistrationRequest;
import com.example.security.full.security.auth.controller.registration.RegistrationService;
import com.example.security.full.security.auth.request.AuthenticationRequest;

import com.example.security.full.security.config.JwtUtils;

import com.example.security.full.security.users.Requests.UsersRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JpaUserDetailsService jpaUserDetailsService;

    private final RegistrationService registrationService;



    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getEmail());

            if (user != null&& user.isEnabled()) {
                response.addCookie(jwtUtils.createCookie(user));
                return ResponseEntity.ok(jwtUtils.generateToken(user));
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }



    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/register")
    public String register(@RequestBody UsersRequest request) {
        return registrationService.register(request);
    }


}
