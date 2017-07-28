package com.example.demo.service;

import com.example.demo.model.UserSession;

/**
 * Created by mayurlathkar on 28/07/17.
 */

public interface TokenService {

    void saveUserTokenForSession(UserSession session);

    void updateUserTokenForSession(UserSession session);

    boolean getTokenForLogOut(String token);

    UserSession findTokenFromDB(String token);

    UserSession findByName(String name);
}
