package auth.dtos.requests;

import auth.dtos.responses.ValidationError;
import auth.dtos.utils.AuthRequestUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    public ValidationError validate() {
        if (!validateEmail())
            return ValidationError.Failure("Invalid email");

        if (!validatePassword())
            return ValidationError.Failure("Invalid password");
        return ValidationError.Success();
    }

    private boolean validateEmail() {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(AuthRequestUtil.emailRegex, email);
    }

    private boolean validatePassword() {
        if (password == null || password.length() < 8) {
            return false;
        }
        return Pattern.matches(AuthRequestUtil.passwordRegex, password);
    }
}
