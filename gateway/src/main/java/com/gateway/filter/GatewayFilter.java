package com.gateway.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class GatewayFilter implements GlobalFilter, Ordered {

    private static final String SECRET_KEY = "secret";

    List<String> publicPaths = List.of("/auth/auth/add-role","/auth/auth/login","/auth/auth/add-user");

    Map<String,List<String>> protectedPath = Map.of(
            "/auth/auth/admin", List.of("ROLE_ADMIN"),
            "/auth/auth/teacher",List.of("ROLE_TEACHER"),
            "/auth/auth/both",List.of("ROLE_TEACHER","ROLE_ADMIN"),
            "/order/order/price",List.of("ROLE_TEACHER","ROLE_ADMIN")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();

        if (isPublicPath(reqPath)) {
            return chain.filter(exchange);
        }

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (header==null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String jwtToken = header.substring(7);

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build()
                    .verify(jwtToken);
            List<String> roles = jwt.getClaim("role").asList(String.class);

            if (!isProtectedPath(reqPath,roles)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }



        } catch (JWTVerificationException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        System.out.print("Hello");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::equalsIgnoreCase);
    }


    private boolean isProtectedPath(String path, List<String> roles) {
        for (Map.Entry<String,List<String>> entry: protectedPath.entrySet() ) {
            String allowedPath = entry.getKey();
            List<String> allowedRoles = entry.getValue();

            if (allowedPath.equalsIgnoreCase(path)) {
                return roles.stream().anyMatch(allowedRoles::contains);
            }

        }
        return false;
    }

}
