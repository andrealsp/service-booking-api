package service_booking_api.core.application.service.auth;

import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.core.domain.auth.port.in.JwtTokenProvider;
import service_booking_api.shared.exceptions.ApplicationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenProviderService implements JwtTokenProvider {

    @Value("${api.secret}")
    private String SECRET;

    public String generateToken(IdentityDetails request) throws ApplicationException {
        return createToken(request);
    }

    private String createToken(IdentityDetails request) throws ApplicationException {
        var expirationTimeMillis = 60 * 60 * 1000;
        var secretKey = generateSecretKey(request);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTimeMillis);
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);

        return Jwts.builder()
                .claim("username", request.getUsername())
                .claim("name", request.getName())
                .claim("userId", request.getId().toString())
                .claim("email", request.getEmail())
                .claim("role", request.getAuthorities())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(decodedKey), SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateSecretKey(IdentityDetails request) throws ApplicationException {
        try {
            log.info("Generating secret key for JWT");
            String baseKey = request.getUsername() + request.getEmail() + SECRET;
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(baseKey.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating validation key");
            throw new ApplicationException(500, "Error generating validation key");
        }
    }

    public Boolean validateToken(IdentityDetails identityDetails, String token) throws ApplicationException {
        try {
            log.debug("Validating JWT token for users: {}", identityDetails.getUsername());

            String secretKey = generateSecretKey(identityDetails);
            byte[] decodedKey = Base64.getDecoder().decode(secretKey);

            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(decodedKey))
                    .build()
                    .parseSignedClaims(token);

            log.debug("Token validation successful for users: {}", identityDetails.getUsername());
            return true;

        } catch (ExpiredJwtException ex) {
            log.warn("Token expired for users: {} - {}",
                    ex.getClaims().getSubject(),
                    ex.getMessage());
            throw new ApplicationException(401, "Token expired. Please log in again.");
        } catch (MalformedJwtException ex) {
            log.warn("Invalid JWT token format: {}", ex.getMessage());
            throw new ApplicationException(401, "Invalid token format.");
        } catch (SecurityException ex) {
            log.warn("Invalid JWT signature for token: {}", token);
            throw new ApplicationException(401, "Invalid token signature.");
        } catch (IllegalArgumentException ex) {
            log.warn("JWT token is null or empty");
            throw new ApplicationException(400, "Token must not be empty.");
        } catch (Exception ex) {
            log.error("Unexpected error during token validation: {}", ex.getMessage(), ex);
            throw new ApplicationException(500, "Error while validating token.");
        }
    }

    public String extractUsername(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return payload.split("\"username\":\"")[1].split("\"")[0];
    }

}
