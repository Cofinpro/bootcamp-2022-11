package com.cofinprobootcamp.backend.auth;

//import com.azure.spring.cloud.autoconfigure.aadb2c.AadB2cOidcLoginConfigurer;
import com.cofinprobootcamp.backend.enums.StandardRoles;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.cofinprobootcamp.backend.config.ProfileConfiguration.FRONTEND_URL;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // with this annotation, @PreAuthorize works with the roles
public class SecurityConfig {


    private final RsaKeyProperties rsaKeys;
    private final UserDetailsServiceImpl userDetailsService;
    // private final AadB2cOidcLoginConfigurer configurer;

    public SecurityConfig(RsaKeyProperties rsaKeys, UserDetailsServiceImpl userDetailsService/*, AadB2cOidcLoginConfigurer configurer*/) {
        this.rsaKeys = rsaKeys;
        this.userDetailsService = userDetailsService;
       // this.configurer = configurer;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .mvcMatchers("/testMSLogin").permitAll()
                        .mvcMatchers("/swagger-ui/*").permitAll()
                        .mvcMatchers("/v3/api-docs/swagger-config").permitAll()
                        .mvcMatchers("/v3/*").permitAll()
                        .mvcMatchers("/api/v1/token").permitAll()
                        .mvcMatchers("/api/v1/token/refresh").permitAll()
                        .mvcMatchers("/api/v1/token/verify").permitAll()
                        // using hasAuthority(), because role is named "SCOPE_ROLE_ROLENAME" --> therefore hasRole would not work, because of the "SCOPE"-prefix
                        .mvcMatchers("/api/v1/roles").hasAuthority("SCOPE_ROLE_ADMIN")
                        .mvcMatchers("/api/v1/roles/*").hasAuthority("SCOPE_ROLE_ADMIN")
                        .mvcMatchers("/api/v1/users").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_ROLE_HR")
                        .mvcMatchers("/api/v1/users/*").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_ROLE_HR")
                        .mvcMatchers("/api/v1/profiles").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_ROLE_USER", "SCOPE_ROLE_HR")
                        .mvcMatchers("/api/v1/profiles/expertises").permitAll()
                        .mvcMatchers("/api/v1/profiles/*").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_ROLE_USER", "SCOPE_ROLE_HR")
                        .mvcMatchers("/api/v1/job-titles").permitAll() // These may be kept for convenience
                        .mvcMatchers("/api/v1/skills").permitAll() // These may be kept for convenience
                        .anyRequest().authenticated() // check, if all other requests should rather be denied?!
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((ex) -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .httpBasic(withDefaults())
               // .apply(configurer)
               // .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
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
