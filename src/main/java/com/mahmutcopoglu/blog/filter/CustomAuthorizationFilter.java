package com.mahmutcopoglu.blog.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahmutcopoglu.blog.constant.AuthConstants;
import com.mahmutcopoglu.blog.dto.ErrorObject;
import com.mahmutcopoglu.blog.security.SecurityConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Builder
@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
       var authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
       if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
           try {
               final var userToken = authorizationHeader.substring("Bearer ".length());
               final var algorithm = Algorithm.HMAC256(secretKey.getBytes());

               var verifier = JWT.require(algorithm).build();
               var decodedJWT = verifier.verify(userToken);
               var username = decodedJWT.getSubject();
               String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
               Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
               Stream.of(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
               var token = new UsernamePasswordAuthenticationToken(username, null,authorities);
               token.setDetails(decodedJWT.getId());
               SecurityContextHolder.getContext().setAuthentication(token);
               filterChain.doFilter(httpServletRequest,httpServletResponse);
           } catch (Exception ex) {
               log.error("Error logging in: {} ",ex.getMessage());
               httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
               httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               httpServletResponse.setHeader(AuthConstants.ERROR, ex.getMessage());

               var error = new ErrorObject(ex.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
               new ObjectMapper().writeValue(httpServletResponse.getOutputStream(),error);
               filterChain.doFilter(httpServletRequest,httpServletResponse);
           }
       }else{
           httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
           httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           httpServletResponse.setHeader(AuthConstants.ERROR, "No token is provided");
           var error = new ErrorObject("No token is provided", HttpServletResponse.SC_UNAUTHORIZED);
            new ObjectMapper().writeValue(httpServletResponse.getOutputStream(),error);
           filterChain.doFilter(httpServletRequest,httpServletResponse);
       }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        return httpServletRequest.getServletPath().contains("/login");

    }
}
