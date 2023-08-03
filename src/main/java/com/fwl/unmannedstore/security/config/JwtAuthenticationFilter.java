package com.fwl.unmannedstore.security.config;

import com.fwl.unmannedstore.security.authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Fire once when connected
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    // Intercept each part to do the filter (Extract data or provide data)
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization"); // Extract JWT authentication token
//        final String jwt;
//        final String userEmail;
//        // Check if correct header is retrieved
//        if (authHeader == null || authHeader.startsWith("Bearer ")) {
//            // Pass to the next filter
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // Extract token from this header (index: 7)
//        jwt = authHeader.substring(7);
//        // Extract user email (Need a class JwtService)
//        userEmail = jwtService.getUserNameFromJwtToken(jwt);
//
//        // Check user email not null
//        // SecurityContextHolder : if authentication is null, meaning the user is not authenticated.
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Check user in database
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            // Check token is valid or not
//            if (jwtService.validateJwtToken(jwt)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                        );
//                // enforce authToken with request
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request));
//                // update authToken to SecurityContextHolder
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        // Pass to the next filter
//        filterChain.doFilter(request, response);
//    }


//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//        return path.startsWith("/usms/signin");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            // perform Jwt-authentication
            try {
                String jwt = parseJwt(request);
                logger.info("jwt: " + jwt);

                if ((jwt != null) && jwtService.validateJwtToken(jwt)) {
                    logger.info("jwt is not null.");
                    String username = jwtService.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    logger.info("User logged in: " + username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.info("SecurityContextHolder set to " + username);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }

            filterChain.doFilter(request, response);

    }

    private String parseJwt (HttpServletRequest request){
        String jwt = jwtService.getJwtFromCookies(request);
        return jwt;
    }

}

