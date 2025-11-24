package com.phuc.payroll.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.phuc.payroll.configuration.AuthenticationRequestInterceptor;
import com.phuc.payroll.dto.response.UserResponse;


@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = {AuthenticationRequestInterceptor.class})
public interface AuthClient {
    @GetMapping("/id/{userId}")
    UserResponse getUserById(@PathVariable Long userId);

    // Note: userExists endpoint doesn't exist in auth-service
    // Use getUserById and handle null/exception for existence check
}
