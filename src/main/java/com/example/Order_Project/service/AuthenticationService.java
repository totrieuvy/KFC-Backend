package com.example.Order_Project.service;

import com.example.Order_Project.dto.request.LoginRequest;
import com.example.Order_Project.dto.request.RegisterRequest;
import com.example.Order_Project.dto.response.LoginResponse;
import com.example.Order_Project.dto.response.RegisterResponse;
import com.example.Order_Project.entity.Account;
import com.example.Order_Project.exception.BadRequestException;
import com.example.Order_Project.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder,@Lazy AuthenticationManager authenticationManager) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        authenticationRepository.findByEmail(registerRequest.getEmail()).ifPresent(account -> {
            throw new BadRequestException("Email already exists");
        });

        authenticationRepository.findByPhone(registerRequest.getPhone()).ifPresent(account -> {
            throw new BadRequestException("Phone number already exists");
        });

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Account newAccount = new Account();
        newAccount.setFullName(registerRequest.getFullName());
        newAccount.setPhone(registerRequest.getPhone());
        newAccount.setEmail(registerRequest.getEmail());
        newAccount.setPassword(encodedPassword);
        newAccount.setRole(registerRequest.getRole());

        authenticationRepository.save(newAccount);

        return new RegisterResponse(
                newAccount.getFullName(),
                newAccount.getPhone(),
                newAccount.getEmail(),
                newAccount.getRole()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails == null) {
                throw new BadRequestException("User details not found");
            }
            Account account = authenticationRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new BadRequestException("User not found"));
            return new LoginResponse(account.getId(), account.getFullName(), account.getPhone(), account.getEmail(), account.getRole());
        } catch (Exception e) {
            throw new BadRequestException("Invalid email or password");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
