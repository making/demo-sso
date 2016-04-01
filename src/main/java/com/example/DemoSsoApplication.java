package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class DemoSsoApplication {
    // property set by spring-cloud-sso-connector
    @Value("${ssoServiceUrl:}")
    String ssoServiceUrl;

    @RequestMapping("/")
    String hello() {
        return "hello!";
    }

    @RequestMapping("/me")
    Object me(Principal principal) {
        return principal;
    }

    @RequestMapping("/logout")
    ResponseEntity<?> logout() {
        if (ssoServiceUrl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body("Not Implemented!");
        } else {
            return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create(ssoServiceUrl + "/logout.do"))
                    .build();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoSsoApplication.class, args);
    }
}
