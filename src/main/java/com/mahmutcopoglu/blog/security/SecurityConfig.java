package com.mahmutcopoglu.blog.security;

import com.mahmutcopoglu.blog.filter.CustomAuthenticationFilter;
import com.mahmutcopoglu.blog.filter.CustomAuthorizationFilter;
import com.mahmutcopoglu.blog.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.concurrent.ThreadPoolExecutor;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${authentication.secret.key}")
    private String secretKey;
    @Value("${authentication.secret.accessTokenExpire}")
    private int accessTokenExpire;
    @Value("${authentication.secret.refreshTokenExpire}")
    private int refreshTokenExpire;

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageSource messageSource;

    private String[] excludingPathsForHttpSec = new String[]{
            "/register",
            "/logout/**",
            "/token/refresh/**",
            "/**/reset-password/**",
            "/**/validate-token/**"
    };

    private String[] excludingPathsForWebSec = new String[]{
            "/register",
            "/logout/**",
            "/token/refresh/**",
            "/**/reset-password/**",
            "/**/validate-token/**"
    };



    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.cors().and()
               .csrf().disable()
               .headers().frameOptions().disable()
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .antMatchers(ArrayUtils.addAll(excludingPathsForHttpSec))
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin().disable()
               .addFilter(CustomAuthenticationFilter.builder().secretKey(secretKey)
                       .accessTokenExpire(accessTokenExpire)
                       .refreshTokenExpire(refreshTokenExpire)
                       .authenticationManager(super.authenticationManager()).messageSource(messageSource).build())
               .addFilterBefore(CustomAuthorizationFilter.builder().secretKey(secretKey).build(), UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor){
        return new DelegatingSecurityContextAsyncTaskExecutor(threadPoolTaskExecutor);
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("async-");
        return executor;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(ArrayUtils.addAll(excludingPathsForWebSec))
                .and()
                .ignoring()
                .antMatchers(POST, "/token/refresh/**");
    }




}
