package auth.services.abstractions;

import auth.models.UserIdentity;
import jakarta.servlet.http.HttpServletRequest;

public interface SecurityService {
    boolean isTokenValid(String token, UserIdentity user);
    String createToken(UserIdentity user);
    String extractSubjectFromToken(String token);
    String AuthenticateUser(HttpServletRequest request);
}
