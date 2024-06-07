package auth.controllers;

import auth.dtos.requests.LoginRequest;
import auth.dtos.requests.RegisterRequest;
import auth.services.abstractions.AuthService;
import auth.services.abstractions.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller("auth")
public class AuthController {
    private final AuthService authService;
    private final SecurityService securityService;

    public AuthController(
            AuthService authService,
            SecurityService securityService) {
        this.authService = authService;
        this.securityService = securityService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest req) {
        var validationRes = req.validate();

        if (!validationRes.isSuccess())
            return new ResponseEntity<>(validationRes.getMessage(), HttpStatus.BAD_REQUEST);

        var res = authService.register(req);
        if (!res.isCreated())
            return new ResponseEntity<>(res.getError(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest req) {
        var validationRes = req.validate();
        if (!validationRes.isSuccess())
            return new ResponseEntity<>(validationRes.getMessage(), HttpStatus.BAD_REQUEST);

        var res = authService.login(req.getEmail(), req.getPassword());

        if (!res.isSuccess())
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        var userId = securityService.AuthenticateUser(request);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        long userIdLong = Long.parseLong(userId);
        var result = authService.getUserInfo(userIdLong);

        if (!result.isSuccess())
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
