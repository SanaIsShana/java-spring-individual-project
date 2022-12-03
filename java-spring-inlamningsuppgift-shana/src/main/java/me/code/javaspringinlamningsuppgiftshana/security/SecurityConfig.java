package me.code.javaspringinlamningsuppgiftshana.security;


import me.code.javaspringinlamningsuppgiftshana.security.JWTLoginFilter;
import me.code.javaspringinlamningsuppgiftshana.security.JWTVerifyFilter;
import me.code.javaspringinlamningsuppgiftshana.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserService userService,
            AuthenticationManager authenticationManager
    )
            throws Exception
    {
        return http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager))
                .addFilterAfter(new JWTVerifyFilter(userService), JWTLoginFilter.class)
                .authorizeRequests()
                .antMatchers("/register")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/file/")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/file/")
                .authenticated()
                .antMatchers(HttpMethod.DELETE, "/file/")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}