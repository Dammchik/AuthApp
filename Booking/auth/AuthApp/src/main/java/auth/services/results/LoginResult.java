package auth.services.results;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private boolean success;
    private String error;
    private String token;

    public static LoginResult Success(String token) {
        return new LoginResult(true, null, token);
    }

    public static LoginResult Failure(String error) {
        return new LoginResult(false, error, null);
    }

}
