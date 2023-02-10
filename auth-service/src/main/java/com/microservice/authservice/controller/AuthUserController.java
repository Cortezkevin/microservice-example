package com.microservice.authservice.controller;

import com.microservice.authservice.dto.AuthUserDTO;
import com.microservice.authservice.dto.TokenDTO;
import com.microservice.authservice.entity.AuthUser;
import com.microservice.authservice.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService service;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO dto){
        TokenDTO tokenDTO = service.login(dto);
        if(tokenDTO == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validate(@RequestParam String token){
        TokenDTO tokenDTO = service.validate(token);
        if(tokenDTO == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDTO dto){
        AuthUser authUser = service.create(dto);
        if( authUser == null )
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }
}
