package com.example.sharing_service_site.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
      .requestMatchers("/login", "/css/**", "/images/**").permitAll()
      // .requestMatchers("/home").hasRole("USER") // 制限ページの設定不足
      .anyRequest().authenticated())
    .formLogin(login -> login
      .loginPage("/login")
      .defaultSuccessUrl("/home")
      .permitAll())
    .logout(logout -> logout
      .logoutUrl("/logout")
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID")
      .permitAll());
    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
