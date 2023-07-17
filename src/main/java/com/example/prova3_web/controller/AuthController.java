package com.example.prova3_web.controller;

import com.example.prova3_web.config.LoginDTO;
import com.example.prova3_web.config.TokenResponse;
import com.example.prova3_web.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public TokenResponse token(@RequestBody @Valid LoginDTO loginDTO) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password()));

        return TokenResponse.builder().token(tokenService.generateToken(authentication)).build();
    }


}
