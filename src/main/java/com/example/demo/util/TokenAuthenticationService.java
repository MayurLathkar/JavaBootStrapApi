package com.example.demo.util;

import com.example.demo.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

/**
 * Created by mayurlathkar on 26/07/17.
 */
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 360000;//864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";

    public static String addAuthentication(User user) {
        String JWT = Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .setIssuer(user.getUser_type())
                .claim("roles", user.getUser_type())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return JWT;
    }

    public static String getAuthenticationToken(String token) {

        if (token != null) {
            // parse the token.
            try {
                token = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject();

                return token;
            } catch (ExpiredJwtException ex) {
                return null;
            }

        }
        return null;
    }

    public static String getIssuer(String validToken) {
        if (validToken != null) {
            try {
                validToken = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(validToken.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getIssuer();

                return validToken;
            } catch (ExpiredJwtException ex) {
                return null;
            }
        }
        return null;
    }

//    public static String checkTokenForValidation(String token) {
//        if (token != null) {
//            //parse the token
//            try {
//                Date tokenExpiryDate = Jwts.parser()
//                        .setSigningKey(SECRET)
//                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                        .getBody()
//                        .getExpiration();
//                if (tokenExpiryDate.compareTo(new Date()) > 0) {
//                    return token;
//                } else
//                    return null;
//            } catch (ExpiredJwtException ex) {
//                return null;
//            }
//        }
//        return null;
//    }
}
