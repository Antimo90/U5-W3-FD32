package antimomandorino.u5w3fd3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il nome deve avere una lunghezza compresa tra 2 e 30 caratteri")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il cognome deve avere una lunghezza compresa tra 2 e 30 caratteri")
        String cognome,
        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,
        @NotBlank(message = "La password è obbligatoria!")
        @Size(min = 4, message = "La password deve avere minimo 4 caratteri")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola ecc...")
        String password) {
}
