package org.revo.Config;

import org.revo.Service.UserService;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Created by ashraf on 18/04/17.
 */
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()/*hasRole("ACTUATOR")*/
                .pathMatchers(HttpMethod.GET, "/api/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/search").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt().and().and().build();
    }

    @Bean
    public AuditorAware<String> aware(UserService userService) {
        return () -> userService.current().blockOptional();
    }
}
