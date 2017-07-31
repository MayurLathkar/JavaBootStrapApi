package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.model.UserSession;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.CustomErrorType;
import com.example.demo.util.CustomSuccessMessage;
import com.example.demo.util.TokenAuthenticationService;
import com.example.demo.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/**
 * Created by mayurlathkar on 25/07/17.
 */
@RestController
@RequestMapping("/api")
public class RoleBasedUserController {

    private static Logger logger = LoggerFactory.getLogger(RoleBasedUserController.class);

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    //************************GET ALL USERS*********************//

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    private ResponseEntity<ArrayList<?>> getAllUsers(@RequestHeader(value = "Authorization") String token) {

        if (!userService.isAuthorized(token))
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorized"), HttpStatus.OK);

        String validToken = TokenAuthenticationService.getIssuer(token);

        if (validToken!= null &&  validToken.equalsIgnoreCase("student")) {
            return new ResponseEntity(userService.findById(tokenService.findTokenFromDB(token).getId()), HttpStatus.OK);
        } else if (validToken!= null && validToken.equalsIgnoreCase("student")) {
            getAllStudents(token);
        } else if (validToken!= null && validToken.equalsIgnoreCase("student")) {
            getAllTeachers(token);
        } else
            return new ResponseEntity(userService.findAllUsers(), HttpStatus.OK);

        return null;
    }


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    private ResponseEntity<ArrayList<?>> getAllStudents(@RequestHeader(value = "Authorization") String token) {
        User user = userService.findByName(TokenAuthenticationService.getAuthenticationToken(token));
        if (user == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorised to access!"), HttpStatus.CONFLICT);

        if (user.getUser_type().equals("teacher"))
            return new ResponseEntity(userService.findAllStudents(), HttpStatus.OK);
        else
            return new ResponseEntity(CustomErrorType.getErrorObject("Please change your role to get access for this!"), HttpStatus.CONFLICT);

    }

    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    private ResponseEntity<ArrayList<?>> getAllTeachers(@RequestHeader(value = "Authorization") String token) {
        User user = userService.findByName(TokenAuthenticationService.getAuthenticationToken(token));
        if (user == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorised to access!"), HttpStatus.CONFLICT);

        if (user.getUser_type().equals("teacher"))
            return new ResponseEntity(userService.findAllTeachers(), HttpStatus.OK);
        else
            return new ResponseEntity(CustomErrorType.getErrorObject("Please change your role to get access for this!"), HttpStatus.CONFLICT);
    }
    //************************CREATE USER*********************//

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    private ResponseEntity<?> createUser(@RequestBody User user) {

        if (userService.isUserExist(user)) {
            logger.error("Unable to create the user. A user with {} name already exist", user.getName());
            return new ResponseEntity(CustomErrorType.getErrorObject(
                    "Unable to create the user. A user with " + user.getName() + " already exists"), HttpStatus.FOUND);
        } else {
            String message = Validation.checkForMissingParameter(user);

            if (!message.equals("success"))
                return new ResponseEntity(CustomErrorType.getErrorObject(message), HttpStatus.CONFLICT);
            else {
                userService.saveUser(user);
                return new ResponseEntity(user, HttpStatus.CREATED);
            }
        }
    }

    //************************GET USER BY ID*********************//

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getUserById(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {

        User user = userService.findById(id);
        if (user == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("User does not exists!"), HttpStatus.CONFLICT);

        UserSession userSession = null;

        if (TokenAuthenticationService.getAuthenticationToken(token) != null)
            userSession = tokenService.findTokenFromDB(token);
        else {
            return new ResponseEntity(CustomErrorType.getErrorObject("Token expired!"), HttpStatus.CONFLICT);
        }

        if (userSession == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorised to access!"), HttpStatus.CONFLICT);

        return new ResponseEntity(user, HttpStatus.OK);
    }


    //************************UPDATE USER BY ID*********************//

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    private ResponseEntity<?> updateUserById(@RequestBody User newUser, @RequestHeader(value = "Authorization") String token) {

        if (newUser == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Parameters missing!"), HttpStatus.UNAUTHORIZED);

        UserSession userSession = null;

        if (TokenAuthenticationService.getAuthenticationToken(token) != null)
            userSession = tokenService.findTokenFromDB(token);
        else {
            return new ResponseEntity(CustomErrorType.getErrorObject("Token expired!"), HttpStatus.CONFLICT);
        }

        if (userSession == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not a authorized user, Please login to get access"), HttpStatus.CONFLICT);

        if (newUser.getName() == null)
            userService.updateUser(userSession.getId(), newUser);
        else
            return new ResponseEntity(CustomSuccessMessage.getSuccessObject("User name can not nbe changed"), HttpStatus.OK);

        return new ResponseEntity(CustomSuccessMessage.getSuccessObject("User data updated successfully"), HttpStatus.OK);
    }


    //************************DELETE USER BY ID*********************//

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted)
            return new ResponseEntity(CustomErrorType.getErrorObject("User deleted successfully"), HttpStatus.OK);
        else
            return new ResponseEntity(CustomErrorType.getErrorObject("User doesn't exists!"), HttpStatus.CONFLICT);
    }
}
