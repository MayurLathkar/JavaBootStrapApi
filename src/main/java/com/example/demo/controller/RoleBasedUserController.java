package com.example.demo.controller;

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
    private ResponseEntity<ArrayList<?>> getAllUsers() {
        return new ResponseEntity(userService.findAllUsers(), HttpStatus.OK);
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

        UserSession userSession = tokenService.findTokenFromDB(token);

        if (userSession == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorised to access!"), HttpStatus.CONFLICT);

        User user = userService.findById(id);
        if (user != null)
            return new ResponseEntity(user, HttpStatus.OK);
        else
            return new ResponseEntity(CustomErrorType.getErrorObject("User does not exists!"), HttpStatus.CONFLICT);
    }


    //************************UPDATE USER BY ID*********************//

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    private ResponseEntity<?> updateUserById(@RequestBody User newUser, @RequestHeader(value = "Authorization") String token) {

        UserSession userSession = tokenService.findTokenFromDB(token);

        if (userSession == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not a authorized user, Please login to get access"), HttpStatus.CONFLICT);

        User user = userService.findByName(TokenAuthenticationService.getAuthenticationToken(token));

        if (user == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Not authorised!"), HttpStatus.UNAUTHORIZED);

        if (userService.findByName(newUser.getName()) != null)
            return new ResponseEntity(CustomErrorType.getErrorObject("User with name " + newUser.getName() + " is already exists"), HttpStatus.CONFLICT);

        if (newUser == null)
            return new ResponseEntity(CustomErrorType.getErrorObject("Parameters missing!"), HttpStatus.UNAUTHORIZED);

        if (newUser != null) {

            if (newUser.getName() == null)
                userService.updateUser(user.getId(), newUser);
            else
                return new ResponseEntity(CustomSuccessMessage.getSuccessObject("User name can not be changed"), HttpStatus.OK);

            if (newUser != null)
                return new ResponseEntity(CustomSuccessMessage.getSuccessObject("User updated successfully"), HttpStatus.OK);
            else
                return new ResponseEntity(CustomErrorType.getErrorObject("User not matches with exiting database!"), HttpStatus.CONFLICT);
        } else
            return new ResponseEntity(CustomErrorType.getErrorObject("User does not exists!"), HttpStatus.CONFLICT);
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
