package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mayurlathkar on 25/07/17.
 */
@Repository
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private static ArrayList<User> users = null;

    @Autowired
    SessionFactory _sessionFactory;

    @Autowired
    TokenService tokenService;


    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }


    @Override
    public User findById(Long id) {
        return (User) getSession().createQuery(
                "from User where id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public User findByName(String name) {
        return (User) getSession().createQuery(
                "from User where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public void saveUser(User user) {
        getSession().save(user);
    }

    @Modifying
    @Override
    public void updateUser(Long id, User user) {
        User found = findById(id);
        if (user.getFirst_name() != null)
            found.setFirst_name(user.getFirst_name());
        if (user.getLast_name() != null)
            found.setLast_name(user.getLast_name());
        if (user.getAddress() != null)
            found.setAddress(user.getAddress());
        if (user.getPhone() != null)
            found.setPhone(user.getPhone());
        if (user.getUser_type() != null)
            found.setUser_type(user.getUser_type());

        getSession().update(found);
    }


    @Override
    public boolean deleteUserById(long id) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<User> findAllUsers() {
        return (ArrayList<User>) getSession().createQuery("from User").list();
    }

    @Override
    public ArrayList<User> findAllStudents() {
        return (ArrayList<User>) getSession().createQuery("from User where user_type = :user_type")
                .setParameter("user_type", "student")
                .list();
    }

    @Override
    public ArrayList<User> findAllTeachers() {
        return (ArrayList<User>) getSession().createQuery("from User where user_type = :user_type")
                .setParameter("user_type", "teacher")
                .list();
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName()) != null;
    }

    @Override
    public boolean isAuthorized(String token) {
        if (tokenService.findTokenFromDB(token) != null)
            return true;
        else return false;
    }
}
