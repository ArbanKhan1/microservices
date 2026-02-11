package com.order.config;

import com.order.filter.OrderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class OrderConfig {

    private final OrderFilter orderFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request->{
            request.requestMatchers("/order/price").hasAnyRole("ADMIN","TEACHER");
            request.anyRequest().authenticated();
        }).addFilterBefore(orderFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
}
