package service_booking_api.core.application.service.auth;

import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.core.domain.auth.port.in.JwtTokenProvider;
import service_booking_api.shared.exceptions.ApplicationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenProviderService implements JwtTokenProvider {

    @Value("${api.secret}")
    private String SECRET;

    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour

    // =========================
    // 🔐 INTERNAL KEY HANDLING
    // =========================
    private byte[] getSigningKey() {
        return SECRET.getBytes(StandardCharsets.UTF_8);
    }

    // =========================
    // 🪪 TOKEN GENERATION
    // =========================
    @Override
    public String generateToken(IdentityDetails request) throws ApplicationException {
        try {
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

            return Jwts.builder()
                    .claim("username", request.getUsername())
                    .claim("given_name", request.getGivenName())
                    .claim("userId", request.getId().toString())
                    .claim("email", request.getEmail())
                    .claim("role", request.getAuthorities())
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(getSigningKey()), SignatureAlgorithm.HS512)
                    .compact();

        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            throw new ApplicationException(500, "Error generating token");
        }
    }

    // =========================
    // ✅ TOKEN VALIDATION
    // =========================
    @Override
    public Boolean validateToken(String token) throws ApplicationException {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(getSigningKey()))
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (ExpiredJwtException ex) {
            log.warn("Token expired: {}", ex.getMessage());
            throw new ApplicationException(401, "Token expired. Please log in again.");

        } catch (Exception ex) {
            log.warn("Invalid token: {}", ex.getMessage());
            throw new ApplicationException(401, "Invalid token");
        }
    }

    // =========================
    // 👤 EXTRACT CLAIMS
    // =========================
    private Claims extractAllClaims(String token) throws ApplicationException {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(getSigningKey()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            throw new ApplicationException(401, "Invalid token");
        }
    }

    @Override
    public String extractUsername(String token) throws ApplicationException {
        return extractAllClaims(token).get("username", String.class);
    }

}