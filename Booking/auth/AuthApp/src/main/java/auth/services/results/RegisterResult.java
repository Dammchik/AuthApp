package auth.services.results;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResult {
    private boolean isCreated;
    private String error;

    public static RegisterResult Success() {
        return new RegisterResult(true, null);
    }

    public static RegisterResult Failure(String error) {
        return new RegisterResult(true, error);
    }
}
