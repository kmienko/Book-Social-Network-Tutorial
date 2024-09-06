package com.kmienko.Book_Social_Network_Tutorial.auth;

import com.kmienko.Book_Social_Network_Tutorial.role.RoleRepository;
import com.kmienko.Book_Social_Network_Tutorial.user.Token;
import com.kmienko.Book_Social_Network_Tutorial.user.TokenRepository;
import com.kmienko.Book_Social_Network_Tutorial.user.User;
import com.kmienko.Book_Social_Network_Tutorial.user.UserRepository;
import com.kmienko.Book_Social_Network_Tutorial.email.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final int CODE_LENGTH = 6;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService; //todo

    public void register(@Valid RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                    // todo - as in tutorial better exception later
                .orElseThrow(() -> new RuntimeException("User role was not initialized"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);


    }

    private void sendValidationEmail(User user) {
        var activationCode = generateAndSaveActivationToken(user);
        //todo - send email with code
    }

    private String generateAndSaveActivationToken(User user) {
        String activationCode = generateActivationCode(CODE_LENGTH);
        var token = Token.builder()
                .token(activationCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();
        tokenRepository.save(token);
        return activationCode;
    }

    private String generateActivationCode(int codeLength) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < codeLength; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
         return codeBuilder.toString();
        }
}
