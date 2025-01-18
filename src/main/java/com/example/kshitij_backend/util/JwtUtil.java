package com.example.kshitij_backend.util;

import com.example.kshitij_backend.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final static String SECRET_KEY = "tT88q8jpDWTpS7gnvwdV9fislqbAuNXI";
    private final static SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static String generateJwtToken(UserModel userModel){
        return Jwts.builder()
                .subject(userModel.getId())
                .claim("name",userModel.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 60)) // 60 hours
                .signWith(KEY)
                .compact();
    }
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        }catch (SignatureException s){
            System.out.println("Signature Exception : "+s.getMessage());
        }
        return false;
    }

    public static String getUserIdByToken(String token){
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(KEY) // Set the secret key
                    .build()
                    .parseSignedClaims(token) // Parse the token
                    .getPayload();           // Retrieve the claims

            return claims.getSubject();
        }catch (Exception e){
            log.error("Error in getting JWT Subject {}",e.getMessage());
        }
        return CommonMessage.FAILED;
    }
}
