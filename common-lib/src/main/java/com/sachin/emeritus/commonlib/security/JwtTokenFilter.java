package com.sachin.emeritus.commonlib.security;

import com.sachin.emeritus.commonlib.security.vo.CustomAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if (JwtUtil.hasAuthHeader()) {
            String token = JwtUtil.getAuthHeader();
            try {
                JwtUtil.validateToken(token);
                CustomAuthenticationToken authentication = new CustomAuthenticationToken(JwtUtil.getUserId(), List.of(new SimpleGrantedAuthority("ROLE_" + JwtUtil.getUserRole())));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }
}
