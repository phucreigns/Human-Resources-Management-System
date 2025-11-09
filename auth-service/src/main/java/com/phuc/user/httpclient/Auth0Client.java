package com.phuc.user.httpclient;

import com.phuc.user.dto.auth0.Auth0TokenResponse;
import com.phuc.user.dto.auth0.Auth0UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth0-client", url = "https://${auth0.domain}")
public interface Auth0Client {

    /**
     * Exchange authorization code thành access token và refresh token.
     * Endpoint: POST /oauth/token (mặc định của Auth0)
     */
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Auth0TokenResponse exchangeCodeForToken(@RequestBody String formData);

    /**
     * Refresh access token bằng refresh token.
     * Endpoint: POST /oauth/token (mặc định của Auth0)
     */
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Auth0TokenResponse refreshToken(@RequestBody String formData);

    /**
     * Lấy thông tin user từ Auth0.
     * Lưu ý: Endpoint "/userinfo" là endpoint chuẩn của OpenID Connect specification,
     * được Auth0 cung cấp, không thể thay đổi thành "/userinfo".
     */
    @GetMapping("/userinfo")
    Auth0UserInfo getUserInfo(@RequestHeader("Authorization") String authorization);

}

