package com.example.bhcbbackend.configurations.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfiguration
{
    private final CustomCorsConfiguration customCorsConfiguration;

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            @Value("${management.endpoints.web.base-path:/actuator}") final String managementEndpointsWebBasePath
    ) throws Exception
    {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(customCorsConfiguration));
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(AntPathRequestMatcher.antMatcher(managementEndpointsWebBasePath + "/**")).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**"))
//                                .hasAnyRole(EServiceUserRole.asString(EServiceUserRole.ADMIN),
//                                        EServiceUserRole.asString(EServiceUserRole.SWAGGER))
//
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
//                                .hasAnyRole(EServiceUserRole.asString(EServiceUserRole.ADMIN),
//                                        EServiceUserRole.asString(EServiceUserRole.SWAGGER))
//
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui.html"))
//                                .hasAnyRole(EServiceUserRole.asString(EServiceUserRole.ADMIN), EServiceUserRole.asString(EServiceUserRole.SWAGGER))
//
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/login")).permitAll()
//
//                                .anyRequest().hasAnyRole(EServiceUserRole.asString(EServiceUserRole.ADMIN), EServiceUserRole.asString(EServiceUserRole.USER)))
//                .oneTimeTokenLogin(ott ->
//                        ott.tokenGeneratingUrl("/api/ott/generate-token")
//                                .defaultSubmitPageUrl("/api/ott/submit-token")
//                                .showDefaultSubmitPage(false)
//                )
//                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    GrantedAuthoritiesMapper grantedAuthoritiesMapper()
    {
        final var mapper = new SimpleAuthorityMapper();
        mapper.setPrefix("");

        return mapper;
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
