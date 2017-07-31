package com.example.demo.model;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by mayurlathkar on 28/07/17.
 */
@Entity
@Table(name = "session")
public class UserSession implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @NotNull
    private Long id;

    @Column(name = "USER_TOKEN")
    @NotNull
    @Size(max = 400)
    private String user_token;

    @Column(name = "USER_NAME")
    @NotNull
    @Size(max = 100)
    private String user_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
