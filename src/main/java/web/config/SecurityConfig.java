package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disabling csrf is insecure - for development
        return http.httpBasic().and().cors().and().csrf().disable()
            .requiresChannel(channel ->
                channel.anyRequest().requiresSecure())
            .authorizeRequests(authorize ->
                authorize.anyRequest().permitAll())
            .build();
    }
}