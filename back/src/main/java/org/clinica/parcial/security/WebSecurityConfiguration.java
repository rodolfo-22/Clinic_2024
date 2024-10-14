package org.clinica.parcial.security;

import jakarta.servlet.http.HttpServletResponse;
import org.clinica.parcial.services.UserService;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.utils.JWTTokenFIlter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenFIlter filter;

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        managerBuilder
                .userDetailsService(identifier -> {
                    User user = userService.findOneByIdentifier(identifier);

                    if (user == null)
                        throw new UsernameNotFoundException("User: " + identifier + ", not found!");

                    return user;
                })
                .passwordEncoder(passwordEncoder);

        return managerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configurar CORS
        http.cors(withDefaults());

        // Http login and csrf disabled
        http.httpBasic(withDefaults()).csrf(csrf -> csrf.disable());

        // Route filter
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/").permitAll()
                        .requestMatchers("/api/users/roles/**").hasAuthority("Administrador")
                        .requestMatchers("/config/user-role").hasAuthority("Administrador")
                        .requestMatchers("/api/appointment/approve/").hasAuthority("Asistente")
                        .requestMatchers("/api/appointment/request/").permitAll()
                        .anyRequest().authenticated()
        );

        // Statelessness
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Unauthorized handler
        http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
            res.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Auth fail!"
            );
        }));

        // JWT filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
