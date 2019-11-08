package com.spendsmart.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.auth")
public class AuthConfiguration {

    private List<String> authorizedRedirectUris;
    private long tokenExpiration;
}
