package com.mahmutcopoglu.blog.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahmutcopoglu.blog.dto.AuthToken;
import com.mahmutcopoglu.blog.dto.LoginRequest;
import com.mahmutcopoglu.blog.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@Builder
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String LOGIN_REQUEST = "loginRequest";
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;
    private final String secretKey;
    private final int accessTokenExpire;
    private final int refreshTokenExpire;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       if(!request.getMethod().equals("POST")){
           throw new AuthenticationServiceException("Authentication method not supported:" + request.getMethod());
       }
       LoginRequest loginRequest;
       if(APPLICATION_JSON_VALUE.equals(request.getHeader("Content-Type"))) {
           loginRequest = this.getLoginRequest(request);
       } else {
           loginRequest = new LoginRequest();
           loginRequest.setUsername(super.obtainUsername(request));
           loginRequest.setPassword(super.obtainPassword(request));
       }
       request.setAttribute(LOGIN_REQUEST, loginRequest);
       var authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
       setDetails(request,authentication);
       return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        var user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        var jwtId = UUID.randomUUID().toString();
        var access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateUtils.addMinutes(new Date(), accessTokenExpire))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withJWTId(jwtId)
                .sign(algorithm);
        var refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateUtils.addMinutes(new Date(), accessTokenExpire))
                .withIssuer(request.getRequestURL().toString())
                .withJWTId(jwtId)
                .sign(algorithm);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), new AuthToken(access_token,refresh_token));
    }

    private LoginRequest getLoginRequest(HttpServletRequest request)
    {
        BufferedReader bufferedReader = null;
        LoginRequest loginRequest = null;
        try {
            bufferedReader = request.getReader();
            loginRequest = new ObjectMapper().readValue(bufferedReader, LoginRequest.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException ex){
                log.error(ex.getLocalizedMessage());
            }
        }
        return loginRequest !=null ? loginRequest: new LoginRequest();
    }
}
