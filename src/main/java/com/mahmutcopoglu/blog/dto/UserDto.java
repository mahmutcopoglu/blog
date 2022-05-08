package com.mahmutcopoglu.blog.dto;


import com.sun.istack.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private Date lastLogin;
}
