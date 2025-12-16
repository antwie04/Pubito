package com.pubito.pubito_backend.security;

import com.pubito.pubito_backend.filter.JwtFilter;
import com.pubito.pubito_backend.services.user_details.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        // roles
                        .requestMatchers(HttpMethod.GET, "/roles/*/users").hasAuthority("ADMIN")
                        .requestMatchers("/roles/**").hasAuthority("ADMIN")

                        // users
                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/*").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasAuthority("ADMIN")

                        // bars
                        .requestMatchers(HttpMethod.POST, "/bars").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/bars/*").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/bars/*").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/bars/*").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/bars/**").authenticated()

                        // menus
                        .requestMatchers(HttpMethod.GET, "/bars/*/menus/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/menus/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/bars/*/menus/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/bars/*/menus/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/bars/*/menus/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/bars/*/menus/**").hasAnyAuthority("OWNER", "ADMIN")

                        // reviews
                        .requestMatchers(HttpMethod.POST, "/bars/*/reviews").authenticated()
                        .requestMatchers(HttpMethod.GET, "/bars/*/reviews").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/reviews/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reviews/*").authenticated()

                        // address
                        .requestMatchers(HttpMethod.GET, "/address/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/address/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/address/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/address/**").hasAnyAuthority("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/address/**").hasAnyAuthority("OWNER", "ADMIN")


                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
