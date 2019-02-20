package com.stroganova.onlineshop.config.security;

import com.stroganova.onlineshop.service.UserDetailsServiceDefault;
import com.stroganova.onlineshop.web.filter.MDCRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceDefault userDetailsServiceDefault;
    private final MDCRequestFilter mdcRequestFilter = new MDCRequestFilter();

    public SecurityConfig(UserDetailsServiceDefault userDetailsServiceDefault) {
        this.userDetailsServiceDefault = userDetailsServiceDefault;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsServiceDefault)
                .passwordEncoder(encoder())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(mdcRequestFilter, WebAsyncManagerIntegrationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/products/add").hasRole("ADMIN")
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/products", true)
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/products")
        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }




}
