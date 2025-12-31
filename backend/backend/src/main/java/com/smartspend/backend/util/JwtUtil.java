package com.smartspend.backend.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
public class JwtUtil {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey123"; // 32+ chars

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // ✅ MUST be SecretKey (not Key)
    private static final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔹 Generate JWT
    public static String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 🔹 Validate + extract email
    public static String extractRole(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
    public static String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
