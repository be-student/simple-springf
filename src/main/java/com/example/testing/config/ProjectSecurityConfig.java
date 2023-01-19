package com.example.testing.config;

import com.example.testing.filter.JwtValidationFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final JwtValidationFilter jwtValidationFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().configurationSource(this::corsConfiguration).and()
                //Todo: csrf 를 추후에 세팅함
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/myCards", "/myBalance", "/myLoans", "/myAccount").authenticated()
                .requestMatchers("/notice", "/contact", "/register", "/api/register", "/free").permitAll()
                .anyRequest().denyAll().and()
                .httpBasic().disable()
                .formLogin().disable()
                .addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class)
                .build();
    }

    private CorsConfiguration corsConfiguration(HttpServletRequest httpServletRequest) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(Duration.ofSeconds(3600));
        return config;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
}
