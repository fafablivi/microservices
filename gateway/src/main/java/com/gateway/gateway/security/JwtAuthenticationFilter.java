package com.gateway.gateway.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter implements Filter {

    private final String secretKey = "enfaitilfaut32caracteresminimumpourquecamarchejecrois";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                httpRequest.setAttribute("username", claims.getSubject());

                chain.doFilter(request, response);
            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
            } catch (ExpiredJwtException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            } catch (UnsupportedJwtException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unsupported JWT token");
            } catch (IllegalArgumentException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT claims string is empty");
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header must be provided");
        }
    }

    @Override
    public void destroy() {
    }
}
