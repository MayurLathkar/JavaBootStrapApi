package com.example.demo.controller;

import com.example.demo.model.Login;
import com.example.demo.model.User;
import com.example.demo.model.UserSession;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomErrorType;
import com.example.demo.util.CustomSuccessMessage;
import com.example.demo.util.TokenAuthenticationService;
import com.example.demo.util.Validation;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by mayurlathkar on 26/07/17.
 */
@RestController
@RequestMapping("/api")
public class UserJwtBasedLoginController {



    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;


    //****************************Register*************************//
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private ResponseEntity<?> registerBasedOnRole(@RequestBody User registerModel) {

        String message = Validation.checkForMissingParameter(registerModel);

        if (message.equals("success")) {

            if (registerModel.getPassword() == null)
                return new ResponseEntity(CustomErrorType.getErrorObject("Password is missing. Please enter the password!"), HttpStatus.CONFLICT);

            User user = userService.findByName(registerModel.getName());

            if (user != null)
                return new ResponseEntity(CustomErrorType.getErrorObject("User with name "+registerModel.getName()+" is already exists"), HttpStatus.CONFLICT);

            userService.saveUser(registerModel);

            return new ResponseEntity(CustomSuccessMessage.getSuccessObject("User registered successfully"), HttpStatus.CREATED);
        } else
            return new ResponseEntity(message, HttpStatus.CONFLICT);
    }


    //****************************Login*************************//
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private ResponseEntity<?> loginWithNameAndPassword(@RequestBody User loginModel) {

        if (loginModel.getName() == null || loginModel.getPassword() == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Please fill the username and password"), HttpStatus.CONFLICT);

        String name = loginModel.getName();
        String password = loginModel.getPassword();

        User user = userService.findByName(loginModel.getName());

        if (user == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("User name not found"), HttpStatus.CONFLICT);

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            return new ResponseEntity(CustomErrorType.getErrorObject("Invalid login. Please check your name and password."), HttpStatus.CONFLICT);
        }

        UserSession userSession;
        String token;

        userSession = tokenService.findByName(loginModel.getName());

        if (userSession != null)
            return new ResponseEntity(CustomErrorType.getErrorObject("User session is already active. Plase logout first!"), HttpStatus.CONFLICT);

        token = TokenAuthenticationService.addAuthentication(user);
        userSession = new UserSession();
        userSession.setId(user.getId());
        userSession.setUser_token(token);
        userSession.setUser_name(user.getName());
        tokenService.saveUserTokenForSession(userSession);

        return new ResponseEntity(CustomSuccessMessage.getSuccessObject(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    private ResponseEntity<?> logoutById(@RequestHeader(value = "Authorization") String token) {
        boolean isDeleted = tokenService.getTokenForLogOut(token);
        if (isDeleted)
            return new ResponseEntity(CustomSuccessMessage.getSuccessObject("You are logged out now!"), HttpStatus.OK);
        else
            return new ResponseEntity(CustomSuccessMessage.getSuccessObject("Not logged in, please login first!"), HttpStatus.OK);
    }
}
