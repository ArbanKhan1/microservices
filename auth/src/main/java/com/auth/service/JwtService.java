package com.auth.service;

import com.auth.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {


    private final static String SECRET_KEY = "secret";
    private final static long EXPIRE_TIME = 86400000;

    public String getTokenByEmailAndRoles(String email,  Collection<? extends GrantedAuthority> authorities) {

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create().withSubject(email)
                .withClaim("role",roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }


}
