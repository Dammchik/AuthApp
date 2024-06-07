package auth.services.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResult {
    private boolean success;
    private String error;
    private String nickname;
    private String email;
    private Timestamp createdAt;

    public static UserInfoResult Success(String nickname, String email, Timestamp createdAt) {
        return new UserInfoResult(true, null, nickname, email, createdAt);
    }

    public static UserInfoResult Failure(String error) {
        return new UserInfoResult(false, error, null, null, null);
    }
}
