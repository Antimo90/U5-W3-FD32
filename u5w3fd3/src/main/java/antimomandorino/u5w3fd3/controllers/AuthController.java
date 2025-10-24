package antimomandorino.u5w3fd3.controllers;

import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.payloads.UserDTO;
import antimomandorino.u5w3fd3.payloads.UserLoginDTO;
import antimomandorino.u5w3fd3.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDTO payload) {
        return authService.register(payload);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated UserLoginDTO payload) {
        return authService.authenticateUserAndGenerateToken(payload);
    }
}
