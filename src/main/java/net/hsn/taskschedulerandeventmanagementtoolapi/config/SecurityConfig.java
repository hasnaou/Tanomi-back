package net.hsn.taskschedulerandeventmanagementtoolapi.config;

import net.hsn.taskschedulerandeventmanagementtoolapi.security.jwt.AuthEntryPointJwt;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.jwt.AuthTokenFilter;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Adjust the allowed origins as needed
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> {
                    auth.antMatchers("/api/auth/signup").permitAll();
                    auth.antMatchers("/api/auth/signin").permitAll();
                    auth.antMatchers(HttpMethod.POST, "/api/projects/add").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.PUT, "/api/projects/edit").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.DELETE, "/api/projects/delete/**").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("USER");

                    auth.antMatchers(HttpMethod.POST, "/api/tasks/add").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.PUT, "/api/tasks/edit").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.DELETE, "/api/tasks/delete/**").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("USER");

                    auth.antMatchers(HttpMethod.POST, "/api/events/add").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.PUT, "/api/events/edit").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.DELETE, "/api/events/delete/**").hasAnyRole("USER");
                    auth.antMatchers(HttpMethod.GET, "/api/events/**").hasAnyRole("USER");
                    auth.antMatchers("/swagger-ui/**").permitAll();
                    auth.antMatchers("/api-docs/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }
}
