package com.example.demo.Config;


import com.example.demo.services.CustomUserDetailService;
import com.example.demo.utility.JwtTOkenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTOkenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String username = jwtTokenUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtTokenUtil.validateToken(token, username)) {

                        Claims claims = jwtTokenUtil.getClaims(token);
                        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());

                        log.info("Username from token {} {}", userDetails.getUsername(),
                                userDetails.getAuthorities().toString());

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (JwtException ex) {
            if (!response.isCommitted()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                Map<String, Object> error = new HashMap<>();
                error.put("timestamp", Instant.now().toString());
                error.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                error.put("error", "Internal server Error");
                error.put("message", "JWT is missing or invalid");

                new ObjectMapper().writeValue(response.getWriter(), error);
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");

            try (PrintWriter writer = response.getWriter()) {
                Map<String, Object> errorBody = new LinkedHashMap<>();
                errorBody.put("timestamp", Instant.now());
                errorBody.put("status", 401);
                errorBody.put("error", "Internal server Error");
                errorBody.put("message", ex.getMessage());
                errorBody.put("path", request.getRequestURI());

                ObjectMapper mapper = new ObjectMapper();
                writer.write(mapper.writeValueAsString(errorBody));
                writer.flush();
            } catch (IOException ignored) {
            }
        }
        chain.doFilter(request, response);
    }
}