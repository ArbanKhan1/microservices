package com.order.client;

import com.auth.model.User;
import com.order.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTH")
public interface AuthClient {
    @GetMapping("/auth/get-user")
    public ClientDto both(@RequestParam("email") String email, @RequestHeader(HttpHeaders.AUTHORIZATION) String header);
}
