package com.nsumba.feed_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nsumba.feed_api.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final JWTVerifier verifier;

    // Accepts HS256 only, and checks the signature and exp
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.verifier = JWT.require(Algorithm.HMAC256(secret)).build();
    }

    public AuthenticatedUser validateToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException("invalid or expired token");
        }

        String sub = jwt.getClaim("sub").asString();
        String username = jwt.getClaim("username").asString();
        // sub must be a positive integer, and username a string
        if (sub == null || !sub.matches("[1-9]\\d{0,8}") || username == null) {
            throw new UnauthorizedException("invalid token payload");
        }
        return new AuthenticatedUser(Integer.parseInt(sub), username);
    }
}
