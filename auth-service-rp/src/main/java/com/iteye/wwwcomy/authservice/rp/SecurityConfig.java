package com.iteye.wwwcomy.authservice.rp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthFilter;

@Configuration
@Order(20)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationManager authManager;

    @Bean
    PreAuthFilter preAuthFilter() throws Exception {
        PreAuthFilter preAuthFilter = new PreAuthFilter();
        preAuthFilter.setAuthenticationManager(authManager);
        return preAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/**").and().addFilter(preAuthFilter()).httpBasic().disable()//
                .authorizeRequests().antMatchers("/authenticate").permitAll()//
                .anyRequest().permitAll().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Configuration
    @Order(30)
    public static class AllowAllWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable().authorizeRequests().anyRequest().permitAll().and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

}
