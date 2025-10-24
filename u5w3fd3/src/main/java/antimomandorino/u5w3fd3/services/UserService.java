package antimomandorino.u5w3fd3.services;

import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.entities.UserRole;
import antimomandorino.u5w3fd3.exceptions.BadRequestException;
import antimomandorino.u5w3fd3.exceptions.NotFoundException;
import antimomandorino.u5w3fd3.payloads.UserDTO;
import antimomandorino.u5w3fd3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.userRepository.findAll(pageable);
    }

    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public User save(UserDTO payload) {

        this.userRepository.findByEmail(payload.email()).ifPresent(user -> {
                    throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
                }
        );

        User newUser = new User();

        newUser.setNome(payload.nome());
        newUser.setCognome(payload.cognome());
        newUser.setEmail(payload.email());
        newUser.setPassword(this.bcrypt.encode(payload.password()));
        newUser.setRuolo(UserRole.NORMALE);

        User savedUser = this.userRepository.save(newUser);


        return savedUser;
    }

    public User findByIdAndUpdate(UUID userId, UserDTO payload) {

        User found = this.findById(userId);


        if (!found.getEmail().equals(payload.email())) {

            this.userRepository.findByEmail(payload.email()).ifPresent(user -> {
                        throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
                    }
            );
        }


        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());
        

        User modifiedUser = this.userRepository.save(found);

        return modifiedUser;
    }


    public void delete(UUID id) {
        User existingUser = findById(id);
        userRepository.delete(existingUser);
    }


}
