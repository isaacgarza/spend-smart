package com.spendsmart.config;

import com.plaid.client.PlaidClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("plaid")
public class PlaidConfiguration {

    private String clientId;

    private String secret;

    private String publicKey;

    private String env;

    @Bean
    public PlaidClient plaidClient() {
        PlaidClient.Builder clientBuilder = PlaidClient.newBuilder()
                .clientIdAndSecret(clientId, secret)
                .publicKey(publicKey);
        switch (env.toLowerCase()) {
            case "development":
                clientBuilder.developmentBaseUrl();
                break;
            case "production":
                clientBuilder.productionBaseUrl();
                break;
            case "sandbox":
            default:
                clientBuilder.sandboxBaseUrl();
        }
        return clientBuilder.build();
    }
}
