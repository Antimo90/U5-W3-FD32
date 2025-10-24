package antimomandorino.u5w3fd3.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventoDTO(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        @NotBlank(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotNull(message = "La data e ora sono obbligatorie")
        @FutureOrPresent(message = "L'evento non può essere nel passato")
        LocalDateTime data,

        @NotBlank(message = "Il luogo è obbligatorio")
        String luogo,

        @Min(value = 1, message = "L'evento deve avere almeno 1 posto disponibile")
        int postiDisponibili
) {
}
