package ch.bbw.onePass.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static final String SECRET_KEY = "";

    public static String extractUserEmail(String token) {
        return null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSiningKey())
                .build()
                .parseClaimsJws()
                .getBody();
    }

    private Key getSiningKey() {

    }
}
