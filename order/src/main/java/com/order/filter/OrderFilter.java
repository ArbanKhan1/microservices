package com.order.filter;

import com.auth.model.User;
import com.order.Service.JwtOrderService;
import com.order.client.AuthClient;
import com.order.dto.ClientDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFilter extends OncePerRequestFilter {
    private final JwtOrderService jwtOrderService;
    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header!=null && header.startsWith("Bearer ")) {
            String jwtToken = header.substring(7);

            String email = jwtOrderService.getEmailUsingToken(jwtToken);

            ClientDto user = authClient.both(email, header);


            if (user!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

                Set<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority("ROLE_"+role)
                ).collect(Collectors.toSet());

                UsernamePasswordAuthenticationToken auth = new
                        UsernamePasswordAuthenticationToken(user,null,authorities);

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }


        }

        filterChain.doFilter(request,response);
    }
}
