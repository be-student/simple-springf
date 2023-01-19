package com.example.testing.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(AUTHORIZATION);
        if (jwt != null) {
            validateToken(jwt);
        }
        filterChain.doFilter(request, response);
    }

    private void validateToken(String header) {
        try {
            validateAuthorizationHeader(header);
            String jwt = StringUtils.substringAfter(header, "Bearer ");
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            Claims claims = io.jsonwebtoken.Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String userId = String.valueOf(claims.get("userId"));
            String authorities = String.valueOf(claims.get("authorities"));
            Authentication auth = new UsernamePasswordAuthenticationToken(userId, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Token received", e);
        }
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "Bearer ")) {
            throw new BadCredentialsException("Invalid Token received");
        }
    }
}
