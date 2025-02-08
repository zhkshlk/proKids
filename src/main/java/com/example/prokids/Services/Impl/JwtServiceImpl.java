package com.example.prokids.Services.Impl;

import com.example.prokids.Model.Token;
import com.example.prokids.Model.User;
import com.example.prokids.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl {
    private TokenRepository tokenRepository;
    @Autowired
    public JwtServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Value("${jwt.secret.access}")
    private String jwtSecretAccessKey;

    @Value("${jwt.secret.refresh}")
    private String jwtSecretRefreshKey;


    private Key getAccessSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretAccessKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getRefreshSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretRefreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Map<String, Object> extraClaim, UserDetails userDetails, Key singKey, int expiration) {
        return Jwts.builder().setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(singKey, SignatureAlgorithm.HS256).compact();
    }

    private String generateToken(UserDetails userDetails, String tokenType) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("roles", customUserDetails.getRoles());
        }

        if (Objects.equals(tokenType, "refresh")) {
            return generateToken(claims, userDetails, getRefreshSigningKey(), 7 * 24 * 60 * 60 * 1000); // 7 дней
        } else {
            return generateToken(claims, userDetails, getAccessSigningKey(), 15 * 60 * 1000); // 15 минут
        }
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, "access");
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, "refresh");
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(getAccessSigningKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return Jwts.parser().setSigningKey(getRefreshSigningKey()).build().parseClaimsJws(token).getBody();
        }
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenRevoked(String token) {
        return tokenRepository.findByToken(token)
                .map(Token::isRevoked)
                .orElse(true);
    }
}
