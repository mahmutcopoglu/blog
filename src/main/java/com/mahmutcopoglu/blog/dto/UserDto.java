package com.mahmutcopoglu.blog.dto;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

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
    private Set<RoleDto> roles = new HashSet<>();
}
