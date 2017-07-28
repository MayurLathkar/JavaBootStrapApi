package com.example.demo.service;

import com.example.demo.model.UserSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mayurlathkar on 28/07/17.
 */
@Repository
@Transactional
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public void saveUserTokenForSession(UserSession session) {
        getSession().save(session);
    }

    @Override
    public void updateUserTokenForSession(UserSession session) {
        getSession().update(session);
    }

//    @Override
//    public UserSession findByToken(Long id, String token) {
//        UserSession userSession = (UserSession) getSession().createQuery("from UserSession where user_id = :id")
//                .setParameter("id", id)
//                .uniqueResult();
//        if (userSession != null && userSession.getUser_token() != null &&
//                userSession.getUser_token().equals(token))
//            return userSession;
//        else return null;
//    }

    @Override
    public UserSession findTokenFromDB(String token) {
        return (UserSession) getSession().createQuery("from UserSession where user_token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

    @Override
    public boolean getTokenForLogOut(String token) {
        UserSession userSession = findTokenFromDB(token);
        if (userSession != null) {
            getSession().delete(userSession);
            return true;
        } else
            return false;
    }

    @Override
    public UserSession findByName(String name) {
        return (UserSession) getSession().createQuery("from UserSession where user_name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}
