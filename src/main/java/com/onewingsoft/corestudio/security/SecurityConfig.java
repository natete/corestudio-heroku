package com.onewingsoft.corestudio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Boot Security configuration Class to provide access to the application resources.
 *
 * @author Ignacio González Bullón <natete981@gmail.com>
 * @since 17 oct. 2015
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;


    @Inject
    private UserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/assets/**");
    }

    /**
     * @see WebSecurityConfigurerAdapter#configure(HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
        .and()
            .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
            .exceptionHandling()
            .accessDeniedHandler(new CustomAccesDeniedHandler())
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler)
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID", "CSRF-TOKEN")
            .permitAll()
        .and()
            .authorizeRequests()
            .antMatchers("/api/accounts/**").hasAuthority("ADMIN")
            .antMatchers("/api/admin/**").hasAuthority("ADMIN")
            .antMatchers("/api/client/**").authenticated()
            .antMatchers("/api/config/**").authenticated()
            .antMatchers("/api/groups/**").authenticated()
            .antMatchers("/api/messages/**").authenticated()
            .antMatchers("/api/pass/**").authenticated()
            .antMatchers("/api/professor/**").permitAll();
    }

    /**
     * @see WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)
     */
    @Inject
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
