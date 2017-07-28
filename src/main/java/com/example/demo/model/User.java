package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by mayurlathkar on 25/07/17.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "USER_TYPE")
    @NotNull
    @Size(max = 100)
    private String user_type;

    @Column(name = "NAME")
    @NotNull
    @Size(max = 100)
    private String user_name;

    @Column(name = "FIRST_NAME")
    @NotNull
    @Size(max = 100)
    private String first_name;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(max = 100)
    private String last_name;

    @Column(name = "PHONE")
    @NotNull
    @Size(max = 20)
    private String phone;

    @Column(name = "ADDRESS")
    @NotNull
    @Size(max = 50)
    private String address;

    @Column(name = "PASSWORD")
    @NotNull
    @Size(max = 20)
    private String password;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return user_name;
    }

    public void setName(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @JsonProperty(value = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
