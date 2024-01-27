package com.francesco.ecommercespring.configuration;

import com.francesco.ecommercespring.support.authentication.JwtAuthConverter;
import com.francesco.ecommercespring.support.authentication.JwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@CrossOrigin
@Configuration

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {//posso mettere crossorigin perche sto aprendo la porta ad attacchi
        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/utenti/**").permitAll()
                .requestMatchers("/prodotti/showall").permitAll()
                .requestMatchers("/prodotti/getprodottobyid/{id}").permitAll()
                .requestMatchers("/prodotti/search/byTipologia").permitAll()
                .requestMatchers("/prodotti/search/**").permitAll()
                .requestMatchers("/ordini/**").permitAll()
                .requestMatchers("/carrello/**").permitAll()
                .requestMatchers("/tipologie/alltipo").permitAll()
                .anyRequest().authenticated().and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(new JwtAuthenticationConverter());

        return http.build();
    }

/*
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:4200/*");
        configuration.addAllowedHeader("http://localhost:4200/*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }*/

}