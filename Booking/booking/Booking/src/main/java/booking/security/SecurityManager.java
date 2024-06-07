package booking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Component
@Scope(value = "singleton")
@Service
public class SecurityManager {
    // Секретный ключ, используемый для подписи токена
    @Value("${jwt.secret}")
    private String jwtSecret;

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
