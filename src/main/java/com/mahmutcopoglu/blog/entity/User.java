package com.mahmutcopoglu.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="first_name", length = 40)
    private String firstName;

    @Column(name="last_name", length = 40)
    private String lastName;

    @Column(name="mobile", length = 20)
    private String mobile;

    @Column(name="email", length = 40)
    private String email;

    @Column(name="user_name", length = 20, unique = true)
    private String username;

    @Column(name="password", length = 30)
    private String password;

    @Column(name="last_login")
    private Date lastLogin;

}
