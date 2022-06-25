package com.tocks.backend.controller.auth;

import com.tocks.backend.configuration.jwt.JwtProvider;
import com.tocks.backend.dto.auth.AuthRequest;
import com.tocks.backend.dto.auth.AuthResponse;
import com.tocks.backend.dto.register.RegisterRequest;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController
{
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider)
    {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.saveUser(registerRequest);
    }
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthRequest authRequest) {
        return userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody AuthResponse response) {
        if (jwtProvider.validateToken(response.getToken())) {
            return new ResponseEntity<>(new MessageResponse("Валидация прошла успешно!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Невалидный токен"), HttpStatus.BAD_REQUEST);
    }
}
