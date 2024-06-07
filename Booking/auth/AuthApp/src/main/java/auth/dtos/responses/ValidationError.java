package auth.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    private boolean success;
    private String message;

    public static ValidationError Success() {
        return new ValidationError(true, null);
    }

    public static ValidationError Failure(String message) {
        return new ValidationError(true, message);
    }
}
