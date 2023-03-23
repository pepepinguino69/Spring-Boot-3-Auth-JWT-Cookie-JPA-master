package com.example.security.full.security.auth.controller.registration;

import com.example.security.full.security.UserSecurity.dao.JpaUserDetailsService;

import com.example.security.full.security.app.repository.CiudadRepository;
import com.example.security.full.security.auth.email.EmailDetails;
import com.example.security.full.security.auth.email.EmailService;
import com.example.security.full.security.auth.request.AuthenticationRequest;

import com.example.security.full.security.config.JwtUtils;
import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.Users;
import com.example.security.full.security.users.repository.UsersRepository;
import com.example.security.full.security.users.service.UsersService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsersService usersService;
    private final EmailValidator emailValidator;


    private final EmailService emailService;

    private final UsersRepository usersRepository;

    private final CiudadRepository ciudadRepository;



    private final AuthenticationManager authenticationManager;

    private final JpaUserDetailsService jpaUserDetailsService;


    private final JwtUtils jwtUtils;


    public String register(UsersRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        Users newUser = new Users();
        newUser.setFirst_name(request.getFirst_name());
        newUser.setLast_name(request.getLast_name());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        newUser.setRoles(request.getRoles());
        newUser.setCiudad(ciudadRepository.findById(request.getCiudad_id()).get());
        String token = usersService.signUpUser(newUser);


        String link = "http://localhost:8080/api/auth/email/confirm?token=" + token;
        EmailDetails details = new EmailDetails();
        details.setContent(link);
        details.setEmail(request.getEmail());
        details.setSubject("registration confirmation");
        emailService.sendSimpleMail(details);
        return token;
    }




    @Transactional
    public String confirmToken(String token) {
        Boolean validToken1=usersRepository.findByConfirmationToken(token).isPresent();
        Boolean validToken = validToken1;
        if (validToken1){
            Boolean validToken2=usersRepository.findByConfirmationToken(token).get().getCreatedOn().plusDays(1).isAfter(LocalDate.now());
            Boolean validToken3=usersRepository.findByConfirmationToken(token).get().getEnabled();
            validToken = validToken1&&validToken2&&!validToken3;
        }
            if(!validToken){
            return "Token incorrecto";
        }
        Users userTBC=usersRepository.findByConfirmationToken(token).get();
        userTBC.setConfirmedOn(LocalDateTime.now().toLocalDate());
        userTBC.setEnabled(Boolean.TRUE);
        usersRepository.save(userTBC);





        return "confirmed";
    }

    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request,
                                               HttpServletResponse response)
    {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword(), new ArrayList<>()));

            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getEmail());
            if (user != null && user.isEnabled()) {
                response.addCookie(jwtUtils.createCookie(user));
                return ResponseEntity.ok(jwtUtils.generateToken(user));
            }
            return ResponseEntity.status(400).body("Error authenticating");
        }
        catch
        (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }
}
