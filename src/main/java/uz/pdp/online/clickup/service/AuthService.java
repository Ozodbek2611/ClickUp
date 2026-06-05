//package uz.pdp.online.clickup.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import uz.pdp.online.clickup.entity.User;
//import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
//import uz.pdp.online.clickup.model.authDto.AuthResponse;
//import uz.pdp.online.clickup.model.authDto.LoginRequest;
//import uz.pdp.online.clickup.model.authDto.RegisterRequest;
//import uz.pdp.online.clickup.repository.UserRepository;
//import uz.pdp.online.clickup.security.JwtProvider;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtProvider jwtProvider;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthResponse register(RegisterRequest registerRequest) {
//        if (userRepository.existsByEmail(registerRequest.getEmail())) {
//            throw new AlreadyExistsException("Email already exists");
//        }
//
//        User user = User.builder()
//                .fullName(registerRequest.getFullName())
//                .email(registerRequest.getEmail())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .enabled(true)
//                .accountNonExpired(true)
//                .accountNonLocked(true)
//                .credentialsNonExpired(true)
//                .build();
//
//        userRepository.save(user);
//        String token = jwtProvider.generateToken(user);
//
//        return new AuthResponse(token);
//    }
//
//    public AuthResponse login(LoginRequest loginRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );
//        } catch (BadCredentialsException e) {
//            throw new RuntimeException("Incorrect email or password");
//        }
//
//        User user = userRepository.findByEmail(loginRequest.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String token = jwtProvider.generateToken(user);
//
//        return new AuthResponse(token);
//    }
//}
package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.ForbiddenException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.model.authDto.AuthResponse;
import uz.pdp.online.clickup.model.authDto.LoginRequest;
import uz.pdp.online.clickup.model.authDto.RegisterRequest;
import uz.pdp.online.clickup.repository.UserRepository;
import uz.pdp.online.clickup.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        // Random 6 xonali kod
        String emailCode = String.valueOf((int) (Math.random() * 900000) + 100000);

        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .emailCode(emailCode)
                .enabled(false)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        userRepository.save(user);

        sendEmail(user.getEmail(), "Your verification code: " + emailCode);

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Incorrect email or password");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token);
    }

    public void verifyEmail(String email, String emailCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));

        if (!emailCode.equals(user.getEmailCode())) {
            throw new ForbiddenException("Email code is incorrect");
        }

        user.setEnabled(true);
        userRepository.save(user);
    }

    boolean sendEmail(String sendingEmail, String text) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("noreply@clickup.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Workspace invitation");
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}