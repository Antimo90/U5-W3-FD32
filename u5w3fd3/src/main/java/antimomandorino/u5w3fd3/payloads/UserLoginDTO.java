package antimomandorino.u5w3fd3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,
        @NotBlank(message = "La password è obbligatoria!")
        String password) {
}