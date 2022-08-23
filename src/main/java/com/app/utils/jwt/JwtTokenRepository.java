package com.app.utils.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JwtTokenRepository {

    private final JwtConfig jwtConfig;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenRepository.class);

    public Tokens generateTokens(String userId) {
        try {
            val payload = new JSONObject();
            payload.put("userId", userId);

            val accessToken = generateToken(jwtConfig.getSecret(), jwtConfig.getAccessExpiration(), payload.toString());
            val refreshToken = generateToken(jwtConfig.getSecret(), jwtConfig.getRefreshExpiration(), payload.toString());

            return new Tokens(accessToken, refreshToken);

        } catch (JSONException err) {
            logger.error("Can not create tokens, json error - " + err);
            return null;
        }
    }

    public String generateToken(String secret, Long expiration, String payload) {
        val id = UUID.randomUUID()
                .toString()
                .replace("-", "");

        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now()
                .plusMinutes(expiration)
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .claim("user", payload)
                .setId(id)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String tokenString) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parse(tokenString);

            return true;

        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException err) {
            if (err instanceof ExpiredJwtException) {
                logger.error("token is expired");
            }
            logger.error(err.getMessage());
            return false;
        }
    }

    public JSONObject parseToken(String tokenString) {
        try {
            val base64EncodedBody = tokenString.split("\\.")[1].getBytes();
            return new JSONObject(
                    new String(Base64.getDecoder().decode(base64EncodedBody))
            );

        } catch (JSONException | ArrayIndexOutOfBoundsException err) {
            logger.error("Couldn't parse token, err - " + err.getMessage());
        }
        return new JSONObject();
    }

    public String fetchTokenFromRequest(HttpServletRequest request) {
        try {
            return request.getHeader("accesstoken");
        } catch (Exception err) {
            logger.error("couldn't get token, err - " + err.getMessage());
        }
        return null;
    }

    @Data
    public static class Tokens {
        private final String accessToken;
        private final String refreshToken;

        Tokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
