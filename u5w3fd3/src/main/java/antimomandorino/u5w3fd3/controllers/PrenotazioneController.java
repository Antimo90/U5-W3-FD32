package antimomandorino.u5w3fd3.controllers;

import antimomandorino.u5w3fd3.entities.Prenotazione;
import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.payloads.PrenotazioneDTO;
import antimomandorino.u5w3fd3.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione create(@RequestBody @Validated PrenotazioneDTO payload,
                               @AuthenticationPrincipal User currentAuthenticatedUser) {
        return prenotazioneService.save(payload, currentAuthenticatedUser);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID prenotazioneId,
                       @AuthenticationPrincipal User currentAuthenticatedUser) {

        prenotazioneService.delete(prenotazioneId, currentAuthenticatedUser);
    }

    @GetMapping("/me")
    public List<Prenotazione> findMyReservations(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return prenotazioneService.findMyReservations(currentAuthenticatedUser);
    }
}
