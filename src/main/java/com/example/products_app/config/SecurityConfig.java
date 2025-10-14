package com.example.products_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtConverter jwtConverter;

    private static String apiRoot = "/products-app/api";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/",
                                "/ping",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers(apiRoot + "/fetchProducts",
                                        apiRoot + "/insertProduct")
                        .hasAnyRole("role_user", "role_admin")
                        .requestMatchers(apiRoot + "/updateProduct",
                                        apiRoot + "/deleteProduct")
                        .hasRole("role_admin")
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt-> jwt.jwtAuthenticationConverter(jwtConverter)
                ));

        return http.build();
    }
}
