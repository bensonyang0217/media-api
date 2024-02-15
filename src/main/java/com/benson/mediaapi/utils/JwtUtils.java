package com.benson.mediaapi.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    private static final String BASE64_SECRET_KEY = "0c8bac35ebeee7085106b6cd95a113b90640521adb9297bc407274923917658cf70a1c568a93981ac401055aca4b82fc463c48d7ac6ae28e8c191f617d65ff0a";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    private static Key generateKey() {
        // 解码Base64编码的密钥
        byte[] decodedKey = Base64.getDecoder().decode(BASE64_SECRET_KEY);
        // 生成并返回密钥
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private static final Key key = generateKey();

    public static String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public static String getUserNameFromToken(String token) {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(BASE64_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(decodedKey);

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Token error", e);
        }
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public static boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Log the error or handle it as per your application's requirement
            logger.error(e.getMessage());
            return false;
        }
    }

}
