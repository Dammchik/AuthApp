package auth.services;

import auth.dtos.requests.RegisterRequest;
import auth.models.Token;
import auth.models.UserIdentity;
import auth.repository.TokenRepositoryImpl;
import auth.repository.UserRepositoryImpl;
import auth.services.abstractions.AuthService;
import auth.services.abstractions.SecurityService;
import auth.services.results.LoginResult;
import auth.services.results.RegisterResult;
import auth.services.results.UserInfoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class AuthServiceImpl implements AuthService
{
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private final UserRepositoryImpl userRepository;
    private final SecurityService securityService;
    private final TokenRepositoryImpl tokenRepository;

    public AuthServiceImpl(
            UserRepositoryImpl userRepository,
            SecurityService securityService,
            TokenRepositoryImpl tokenRepository) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public RegisterResult register(RegisterRequest req) {
        var existingUser = userRepository.findByEmail(req.getEmail());
        if (existingUser != null) {
            return RegisterResult.Failure("User with this email already exists");
        }

        var user = UserIdentity.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .nickname(req.getNickname())
                .build();

        userRepository.save(user);
        return RegisterResult.Success();
    }

    @Override
    @Transactional
    public LoginResult login(String email, String password) {
        var existingUser = userRepository.findByEmail(email);

        if (existingUser == null)
            return LoginResult.Failure("User with this email is not exists");

        if (!existingUser.getPassword().equals(password))
            return LoginResult.Failure("Passwords do not match");

        var token = securityService.createToken(existingUser);

        var tokenEntity = Token.builder()
                .token(token)
                .expiryDate(new Date(jwtExpiration))
                .userId(existingUser.getId())
                .build();

        // delete previous token
        tokenRepository.deleteByUserId(existingUser.getId());
        // save new token
        tokenRepository.save(tokenEntity);

        return LoginResult.Success(token);
    }

    @Override
    public UserInfoResult getUserInfo(long userId) {
        var userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty())
            return UserInfoResult.Failure("User not found");

        var user = userEntity.get();
        return UserInfoResult.Success(user.getNickname(), user.getEmail(), user.getCreatedAt());
    }
}
