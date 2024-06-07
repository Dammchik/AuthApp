package auth.services;

import auth.models.UserIdentity;
import auth.services.abstractions.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@Scope(value = "prototype")
public class SecurityServiceImpl implements SecurityService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Override
    public boolean isTokenValid(String token, UserIdentity user) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(jwtSecret))
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            return (email.equals(user.getEmail()) && !isTokenExpired(claims));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String createToken(UserIdentity user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(
                        SignatureAlgorithm.HS256,
                        Base64.getDecoder().decode(jwtSecret)
                )
                .compact();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String extractSubjectFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(jwtSecret))
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            // Обработка ошибки подписи токена
            return null;
        }
    }

    public String AuthenticateUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return null;
        }

        // Удаление слова "Bearer " из токен
        String token = authorizationHeader.substring(7);

        var userId = extractSubjectFromToken(token);

        return userId;
    }
}
