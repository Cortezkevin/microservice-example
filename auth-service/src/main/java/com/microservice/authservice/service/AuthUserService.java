package com.microservice.authservice.service;

import antlr.Token;
import com.microservice.authservice.dto.AuthUserDTO;
import com.microservice.authservice.dto.NewUserDTO;
import com.microservice.authservice.dto.RequestDTO;
import com.microservice.authservice.dto.TokenDTO;
import com.microservice.authservice.entity.AuthUser;
import com.microservice.authservice.repository.AuthUserRepository;
import com.microservice.authservice.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthUser create(NewUserDTO dto){
        Optional<AuthUser> user = repository.findByUsername(dto.getUsername());
        if( user.isPresent() ){
            return null;
        }

        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(password)
                .role(dto.getRole())
                .build();

        return repository.save(authUser);
    }

    public TokenDTO login(AuthUserDTO dto){
        Optional<AuthUser> user = repository.findByUsername(dto.getUsername());
        if( !user.isPresent() )
            return null;
        if( passwordEncoder.matches(dto.getPassword(), user.get().getPassword() ))
            return new TokenDTO(jwtProvider.createToken(user.get()));
        return null;
    }

    public TokenDTO validate(String token, RequestDTO dto){
        if( !jwtProvider.validate(token, dto) )
            return null;
        String username = jwtProvider.getUsernameFromToken(token);
        if(!repository.findByUsername(username).isPresent())
            return null;
        return new TokenDTO(token);
    }

}
