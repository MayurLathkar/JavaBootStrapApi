package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayurlathkar on 25/07/17.
 */
public interface UserService {

    User findById(Long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(Long id, User user);

    boolean deleteUserById(long id);

    ArrayList<User> findAllUsers();

    ArrayList<User> findAllStudents();

    ArrayList<User> findAllTeachers();

    boolean isAuthorized(String token);

    void deleteAllUsers();

    boolean isUserExist(User user);
}
