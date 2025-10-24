package antimomandorino.u5w3fd3.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID dell'evento è obbligatorio per la prenotazione")
        UUID eventoId
) {
}