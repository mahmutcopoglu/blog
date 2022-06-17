package com.mahmutcopoglu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthToken {

    private String accessToken;
    private String refreshToken;

    public AuthToken(String accessToken){
        this.accessToken = accessToken;
    }
}
