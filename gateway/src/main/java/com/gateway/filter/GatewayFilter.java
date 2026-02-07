package com.gateway.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GatewayFilter implements GlobalFilter, Ordered {

    List<String> publicPaths = List.of("/auth/auth/add-role","/auth/auth/login","/auth/auth/add-user");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        String reqPath = exchange.getRequest().getURI().getPath();


        if (isPublicPath(reqPath)) {
            return chain.filter(exchange);
        }



        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::equalsIgnoreCase);
    }
}
