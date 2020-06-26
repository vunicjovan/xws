package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JWTUtil {

    private String SECRET_KEY = ")sd.2rPdl9nMew8s.2RPl3X";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 40))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public List<String> validateToken(String token) {
        try {
            isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token has expired!");
        }

        Claims claims = extractAllClaims(token);
        ArrayList<LinkedHashMap<String, String>> authorityClaims = (ArrayList<LinkedHashMap<String, String>>) claims.get("authorities");
        List<String> authorities = new ArrayList<>();
        authorityClaims.forEach(keyValue -> authorities.add(keyValue.get("authority")));
        authorities.add(claims.getSubject());
        return authorities;
    }
}
