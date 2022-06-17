package com.mahmutcopoglu.blog.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mahmutcopoglu.blog.dto.AuthToken;
import com.mahmutcopoglu.blog.dto.ErrorObject;
import com.mahmutcopoglu.blog.service.UserService;
import com.mahmutcopoglu.blog.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RefreshTokenController {

    @Value("${authentication.secret.key}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    @PostMapping("token/refresh")
    public Object refresh(@RequestBody final String refreshtoken,
                          HttpServletRequest response,
                          HttpServletRequest request) throws IOException {
        final var algorithm = Algorithm.HMAC256(secretKey.getBytes());
        var verifier = JWT.require(algorithm).build();
        try {
            var refreshTokenDecoded = verifier.verify(refreshtoken);
            var user = userDetailsService.loadUserByUsername(refreshTokenDecoded.getSubject());
            var accessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
            return new AuthToken(accessToken);
        }catch (RuntimeException ex) {
            log.error("New access token could not be generated. Refresh token: [{}]",refreshtoken);
        }
        return new ErrorObject("Refresh token is not valid", HttpServletResponse.SC_BAD_REQUEST);
    }


}
