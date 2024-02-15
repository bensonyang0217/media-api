package com.benson.mediaapi.config;

import com.benson.mediaapi.filter.JwtAuthFilter;
import com.benson.mediaapi.service.users.CunstomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    CunstomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        http.csrf((csrf) -> csrf.disable())
                .httpBasic((httpBasic) -> httpBasic.disable())
                .formLogin((formLogin) -> formLogin.disable())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(handler -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(policy -> policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

}
