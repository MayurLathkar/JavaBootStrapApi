package com.example.demo.model;

import java.io.Serializable;

/**
 * Created by mayurlathkar on 26/07/17.
 */
public class Login implements Serializable {

    private String username;

    private String paasword;

    public String getPaasword() {
        return paasword;
    }

    public String getUsername() {
        return username;
    }
}
