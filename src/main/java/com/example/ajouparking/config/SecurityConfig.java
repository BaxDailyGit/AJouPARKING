package com.example.ajouparking.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    이거 왜 안되지?
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return web -> web.ignoring()
//                .requestMatchers("/static/**","/templates/**");
//    }
    @Bean
    public WebSecurityCustomizer configure(){
       return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/","/auth/signin","/auth/signup").permitAll()
                                .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                        //.requestMatchers("/admin").hasRole("ADMIN")
                        //.requestMatchers("/").hasAnyRole("ADMIN","USER")

                );

        http
                .formLogin((auth)->auth.loginPage("/auth/signin")
                        .loginProcessingUrl("/auth/signin")
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                );

        http
                .logout((auth) -> auth
                .logoutUrl("/auth/signout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        http
                .csrf((auth)->auth.disable());


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
