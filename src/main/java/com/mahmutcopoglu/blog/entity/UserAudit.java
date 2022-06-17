package com.mahmutcopoglu.blog.entity;

import com.mahmutcopoglu.blog.enums.LoginResultType;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name="user_audit")
public class UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Date loginDate;

    @Enumerated(EnumType.STRING)
    private LoginResultType loginResult;

    public void setUser(User user){
        this.user = user;
    }
    public void setLoginDate(Date loginDate){
        this.loginDate = loginDate;
    }
    public void setLoginResult(LoginResultType loginResult) {
        this.loginResult = loginResult;
    }



}
