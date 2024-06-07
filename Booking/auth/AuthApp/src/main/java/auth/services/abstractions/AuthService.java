package auth.services.abstractions;

import auth.dtos.requests.RegisterRequest;
import auth.services.results.LoginResult;
import auth.services.results.RegisterResult;
import auth.services.results.UserInfoResult;

public interface AuthService
{
    RegisterResult register(RegisterRequest req);
    LoginResult login(String login, String password);
    UserInfoResult getUserInfo(long userId);
}
