package uz.pdp.online.clickup.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online.clickup.notification.service.EmailService;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.common.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.common.exceptions.ForbiddenException;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.audit.dto.AuthResponseDto;
import uz.pdp.online.clickup.audit.dto.LoginRequest;
import uz.pdp.online.clickup.audit.dto.RegisterRequest;
import uz.pdp.online.clickup.audit.dto.VerifyRequest;
import uz.pdp.online.clickup.user.repository.UserRepository;
import uz.pdp.online.clickup.common.security.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public void register(RegisterRequest registerRequest) {
        log.debug("Registration request started for email: {}", registerRequest.getEmail());

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsException("Email already exists: " + registerRequest.getEmail());
        }

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

        emailService.sendVerificationCode(user.getEmail(), emailCode);

        log.info("User registered successfully. ID: {}, Email: {}", user.getId(), user.getEmail());
    }

    public AuthResponseDto login(LoginRequest loginRequest) {
        log.debug("Authentication request started for email: {}", loginRequest.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for email: {} - Bad credentials", loginRequest.getEmail());
            throw e;
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("User record not found during login. Email: {}", loginRequest.getEmail());
                    return new NotFoundException("User not found with email: " + loginRequest.getEmail());
                });

        log.info("User authenticated successfully. Email: {}", user.getEmail());

        String token = jwtProvider.generateToken(user);
        return new AuthResponseDto(token);
    }

    public void verifyEmail(VerifyRequest verifyRequest) {
        log.debug("Email verification request started for email: {}", verifyRequest.getEmail());

        User user = userRepository.findByEmail(verifyRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + verifyRequest.getEmail()));

        if (!verifyRequest.getEmailCode().equals(user.getEmailCode())) {
            log.warn("Incorrect email verification code entered for email: {}", verifyRequest.getEmail());
            throw new ForbiddenException("Invalid verification code");
        }

        user.setEnabled(true);
        userRepository.save(user);
        log.info("Email verified successfully. Account enabled for email: {}", user.getEmail());
    }
}
