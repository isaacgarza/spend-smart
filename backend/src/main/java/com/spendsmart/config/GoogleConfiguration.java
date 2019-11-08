package com.spendsmart.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.security.oauth2.client.registration.google")
public class GoogleConfiguration {

    private String provider;
    private String clientId;
    private String clientSecret;
    private String clientAuthenticationMethod;
    private String authorizationGrantType;
    private String redirectUri;
    private Set<String> scope;
    private String clientName;
}
