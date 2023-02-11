package com.microservice.authservice.security;

import com.microservice.authservice.dto.RequestDTO;
import com.microservice.authservice.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("{jwt.secret}")
    private String secret;

    //una vez se inyecte el componente automaticamente codifica el secret
    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Autowired
    private RouteValidator routeValidator;

    public String createToken(AuthUser authUser){
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getUsername());
        claims.put("id", authUser.getId());
        claims.put("role", authUser.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validate(String token, RequestDTO dto) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception ex){
            return false;
        }
        if(!isAdmin(token) && routeValidator.isAdminPath(dto))
            return false;
        return true;
    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return "bad token";
        }
    }

    private boolean isAdmin(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
    }
}
