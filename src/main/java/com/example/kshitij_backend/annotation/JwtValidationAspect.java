package com.example.kshitij_backend.annotation;

import com.example.kshitij_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtValidationAspect {

    private final HttpServletRequest request;

    public JwtValidationAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(com.example.kshitij_backend.annotation.ValidateJwtToken.java)")
    public void validateJwt() {
        String token = request.getHeader("X-Authorization");
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Missing or invalid authorization header");
        }
        if(JwtUtil.validateToken(token))
            return;
        throw new RuntimeException("Invalid Token");
    }
}
