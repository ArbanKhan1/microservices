package com.order.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtOrderService {
    private final static String SECRET_KEY = "secret";

    public String getEmailUsingToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build()
                .verify(token)
                .getSubject();
    }
}
