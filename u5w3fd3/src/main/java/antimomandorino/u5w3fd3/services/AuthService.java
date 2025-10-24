package antimomandorino.u5w3fd3.services;

import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.entities.UserRole;
import antimomandorino.u5w3fd3.exceptions.BadRequestException;
import antimomandorino.u5w3fd3.exceptions.UnauthorizedException;
import antimomandorino.u5w3fd3.payloads.UserDTO;
import antimomandorino.u5w3fd3.payloads.UserLoginDTO;
import antimomandorino.u5w3fd3.repositories.UserRepository;
import antimomandorino.u5w3fd3.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public User register(UserDTO payload) {
        // Controllo email unica
        this.userRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });

        User newUser = new User();
        newUser.setNome(payload.nome());
        newUser.setCognome(payload.cognome());
        newUser.setEmail(payload.email());
        newUser.setPassword(this.bcrypt.encode(payload.password()));

        newUser.setRuolo(UserRole.NORMALE);

        return this.userRepository.save(newUser);
    }

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {

        User foundUser = userRepository.findByEmail(payload.email()).orElseThrow(() -> new UnauthorizedException("Credenziali non valide."));


        if (!bcrypt.matches(payload.password(), foundUser.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide.");
        }

       
        return jwtTools.createToken(foundUser);
    }
}
