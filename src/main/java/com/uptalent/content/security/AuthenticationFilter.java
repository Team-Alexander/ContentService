package com.uptalent.content.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String userIdHeader = request.getHeader("User-Id");
        String userRoleHeader = request.getHeader("User-Role");

        if (userIdHeader != null && userRoleHeader != null) {
            Authentication authentication = getAuthentication(userIdHeader, userRoleHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String userIdHeader, String userRoleHeader) {
        long id = Long.parseLong(userIdHeader);
        GrantedAuthority authority = new SimpleGrantedAuthority(userRoleHeader);
        return new UsernamePasswordAuthenticationToken(id, null, List.of(authority));
    }
}