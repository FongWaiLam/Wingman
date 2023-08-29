package com.fwl.unmannedstore.security.authentication;

import com.fwl.unmannedstore.security.entity.Role;
import com.fwl.unmannedstore.security.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    @Value("${jwtSecret}")
    private String jwtSecretKey;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${jwtCookieName}")
    private String jwtCookie;


    // Path represent the specific path that this cookie uses for
    // Browser will send request with this cookie only for paths under this path
    public ResponseCookie generateJwtCookie(User userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), userPrincipal.getRole());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/usms").maxAge(jwtExpirationMs).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/usms").build();
        return cookie;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    // Subject is the email
    public String getUserNameFromJwtToken(String token) {
//        return extractClaim(token, Claims::getSubject);
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    // Java utils Function - A function passed as parameter
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

    public String generateTokenFromUsername(String username, Role role) {
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating JWT token: {}", e.getMessage());
            return null;
        }
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

//    public boolean validateJwtToken(String token) {
//        final String userEmail = getUserNameFromJwtToken(token);
//        // Check user in database
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//        return (userDetails.getUsername().equals(userEmail)) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

    // Signing Key a secrete used to digitally sign the JWT
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes); // Algorithm
    }
}
