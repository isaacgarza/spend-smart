package com.spendsmart.security.token;

import com.spendsmart.config.AuthConfiguration;
import com.spendsmart.config.GoogleConfiguration;
import com.spendsmart.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    private final GoogleConfiguration googleConfiguration;
    private final AuthConfiguration authConfiguration;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public TokenProvider(AuthConfiguration authConfiguration, GoogleConfiguration googleConfiguration) {
        this.authConfiguration = authConfiguration;
        this.googleConfiguration = googleConfiguration;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + authConfiguration.getTokenExpiration());

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(googleConfiguration.getClientSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.", ex);
        }
        return false;
    }

    private SecretKeySpec getSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(googleConfiguration.getClientSecret());
        return new SecretKeySpec(apiKeySecretBytes, TokenProvider.signatureAlgorithm.getJcaName());
    }
}
