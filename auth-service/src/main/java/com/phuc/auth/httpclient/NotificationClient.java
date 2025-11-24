package com.phuc.auth.httpclient;

import com.phuc.auth.configuration.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "notification-client", url = "${notification.service.url}", configuration = {AuthenticationRequestInterceptor.class})
public interface NotificationClient {

    @PostMapping("/notification/api/v1/notifications/send-email")
    Map<String, Object> sendEmail(@RequestBody Map<String, Object> request);
}

