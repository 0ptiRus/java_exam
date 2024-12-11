package ru.topacademy.socialnetwork.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                               .username("user")
                               .password(encoder.encode("password"))
                               .roles("USER")
                               .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
            	.requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
            	.requestMatchers("/posts/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")           // Custom login page
                    .loginProcessingUrl("/login")  // URL to submit the login form
                    .usernameParameter("email")    // Parameter name for username
                    .passwordParameter("password") // Parameter name for password
                    .defaultSuccessUrl("/feed", true)  // Redirect after successful login
                    .failureUrl("/login?error=true") // Redirect after failed login
                )
            .logout(logout -> logout
            	            .logoutUrl("/logout") // URL for logout
            	            .logoutSuccessUrl("/login?logout") // Redirect after logout
            	            .invalidateHttpSession(true) // Invalidate session
            	            .deleteCookies("JSESSIONID") // Remove session cookie
            	            .permitAll())
        	.userDetailsService(customUserDetailsService);
        return http.build();
    }
}