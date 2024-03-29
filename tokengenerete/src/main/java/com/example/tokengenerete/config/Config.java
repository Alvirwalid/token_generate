package com.example.tokengenerete.config;


import com.example.tokengenerete.filter.JwtFilter;
import com.example.tokengenerete.service.UserDetailsServiceImp;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//@Data
@Configuration
@EnableWebSecurity
public class Config {

    private  final UserDetailsServiceImp userDetailsServiceImp;
    private  final JwtFilter jwtFilter;

    public Config(UserDetailsServiceImp userDetailsServiceImp, JwtFilter jwtFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws  Exception{

        return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->
                                req.requestMatchers("/login/**","/register/**")
                        .permitAll()
                                        .requestMatchers("/admin_only/**").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/user_only/**").hasAnyAuthority("USER")

                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
