package com.app.security;

import com.app.security.filters.FiltersFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final FiltersFactory filtersFactory;

    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                .csrf().disable()
                //public accessible
                .authorizeRequests().antMatchers("authorizationUser").permitAll().and()
                .authorizeRequests().antMatchers("/createNewUser").permitAll().and()
                //protected routes
                .authorizeRequests().antMatchers("getAllUserOrders").authenticated().and()
                .authorizeRequests().antMatchers("/addNewOrder").authenticated().and()
                .authorizeRequests().antMatchers("/deleteUsersOrder").authenticated().and()
                .authorizeRequests().antMatchers("/updateUserOrder").authenticated().and()
                //filters
                .addFilterBefore(filtersFactory.getJwtFilter(), AnonymousAuthenticationFilter.class)
                .build();
    }
}
