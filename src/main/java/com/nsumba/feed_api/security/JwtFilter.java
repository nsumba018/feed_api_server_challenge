package com.nsumba.feed_api.security;

import com.nsumba.feed_api.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTH_USER = "authUser";

    private final JwtUtil jwtUtil;

    // Only POST /posts and POST /posts/{id}/like need a token
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isProtected = path.equals("/posts") || path.matches("/posts/[^/]+/like");
        return !("POST".equals(request.getMethod()) && isProtected);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                throw new UnauthorizedException("missing bearer token");
            }
            AuthenticatedUser user = jwtUtil.validateToken(header.substring(7));
            request.setAttribute(AUTH_USER, user);
        } catch (UnauthorizedException e) {
            // I write the 401 here because @RestControllerAdvice does not catch errors from filters
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
