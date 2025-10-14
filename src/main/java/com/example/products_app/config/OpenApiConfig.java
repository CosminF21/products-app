package com.example.products_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${keycloak.host-url}")
    private String keycloakUrl;

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info().title("Product Api").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("keycloak"))
                .components(new Components().addSecuritySchemes("keycloak", oauthScheme()));
    }

    private SecurityScheme oauthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(oAuthFlows());
    }

    private OAuthFlows oAuthFlows() {
        return new OAuthFlows().password(passwordFlow());
    }

    private OAuthFlow passwordFlow() {
        String token = String.format("%s/protocol/openid-connect/token", keycloakUrl);
        return new OAuthFlow()
                .tokenUrl(token)
                .scopes(new Scopes().addString("openid", "OpenID Connect scope"));
    }
}
