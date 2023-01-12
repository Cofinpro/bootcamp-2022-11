package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.exceptions.UserIsLockedException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.cofinprobootcamp.backend.config.Constants.AUTHORITY_PREFIX;
import static com.cofinprobootcamp.backend.config.ProfileConfiguration.FRONTEND_URL;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(RsaKeyProperties rsaKeys, UserDetailsServiceImpl userDetailsService) {
        this.rsaKeys = rsaKeys;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(
                        //@FunctionalInterface Customizer<T> with method customize() as lambda
                        (auth) -> auth
                            .mvcMatchers("/swagger-ui/*").permitAll()
                            .mvcMatchers("/v3/api-docs/swagger-config").permitAll()
                            .mvcMatchers("/v3/*").permitAll()
                            .mvcMatchers("/api/v1/token").permitAll()
                            .mvcMatchers("/api/v1/token/refresh").permitAll()
                            .mvcMatchers("/api/v1/token/verify").permitAll()
                            .anyRequest().authenticated() // check, if all other requests should rather be denied?!
                )
                .oauth2ResourceServer(
                        //@FunctionalInterface Customizer<T> with method customize() as lambda
                        (oauth2) -> {
                            oauth2.jwt()
                                    .jwtAuthenticationConverter(customJwtAuthenticationConverter());
                            oauth2.authenticationEntryPoint(customAuthEntryPoint());
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        //@FunctionalInterface Customizer<T> with method customize() as lambda
                        (ex) -> ex
                            .authenticationEntryPoint(customAuthEntryPoint())
                            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        var authProvider = new CustomDaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        // Anonymous implementation of quasi-functional interface UserDetailsChecker with method check(UserDetails ud)
        // with lambda
        authProvider.setPreAuthenticationChecks(toCheck -> {
            if (!toCheck.isAccountNonLocked()) {
                throw new UserIsLockedException("Einloggen nicht möglich.");
            }
        });
        return new ProviderManager(authProvider);
    }

    @Bean
    public AuthenticationEntryPoint customAuthEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        CustomJwtAuthenticationConverter converter = new CustomJwtAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    @Bean
    public CustomJwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        return new CustomJwtGrantedAuthoritiesConverter();
    }

    /*
     * This method is necessary s.t. role prefix can be used in annotation queries
     */
    @Bean
    public String authorityPrefix() {
        return AUTHORITY_PREFIX;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> t) {
                t.configurationSource(getCorsConfiguration());
            }
        };
    }

    /**
     * Config for Cors-Policy
     * @return
     */
   private CorsConfigurationSource getCorsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of(FRONTEND_URL));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
