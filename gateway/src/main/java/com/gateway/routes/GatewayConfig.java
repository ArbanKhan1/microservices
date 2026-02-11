package com.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("auth-service",f->f.path("/auth/**")
                .filters(r->r.rewritePath("/auth/(?<seg>.*)", "/${seg}"))
                .uri("lb://AUTH"))

                .route("order-service",f->f.path("/order/**")
                        .filters(r->r.rewritePath("/order/(?<seg>.*)", "/${seg}"))
                        .uri("lb://ORDER")).build();
    }

}
