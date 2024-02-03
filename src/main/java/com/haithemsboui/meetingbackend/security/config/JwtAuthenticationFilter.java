package com.haithemsboui.meetingbackend.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //todo: replace it by adding it to white url list
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        // extract the JWT Token stored through the header(Authorization)
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // check if the token does not exist, or it has an unsupported bear token then stop the process

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // copy the content of the token which is after "bearer "
        jwt = authHeader.substring(7);
        // extract the email from the jwt token
        userEmail = jwtService.extractUsername(jwt);
        // check if email exists and the user not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetch UserDetails of extracted email (it will throw exception if not found)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            logger.info("User roles: " + userDetails.getAuthorities());

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}









//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        // extract the JWT Token stored through the header(Authorization)
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        // check if the token does not exist, or it has an unsupported bear token then stop the process
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // copy the content of the token which is after "bearer "
//        jwt = authHeader.substring(7);
//        // extract the email from the jwt token
//        userEmail = jwtService.extractUsername(jwt);
//        // check if email exists and the user not authenticated
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // fetch UserDetails of extracted email (it will throw exception if not found)
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//
//    }
//}
