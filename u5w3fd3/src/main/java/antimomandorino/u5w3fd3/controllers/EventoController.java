package antimomandorino.u5w3fd3.controllers;

import antimomandorino.u5w3fd3.entities.Evento;
import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.payloads.EventoDTO;
import antimomandorino.u5w3fd3.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping

    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {

        return this.eventoService.findAll(page, size, sortBy);
    }

    @GetMapping("/{eventoId}")
    public Evento findById(@PathVariable UUID eventoId) {
        return eventoService.findById(eventoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')") // Accesso limitato
    public Evento create(@RequestBody @Validated EventoDTO payload,
                         @AuthenticationPrincipal User currentAuthenticatedUser) {
        return eventoService.save(payload, currentAuthenticatedUser);
    }

    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento findByIdAndUpdate(@PathVariable UUID eventoId,
                                    @RequestBody @Validated EventoDTO payload,
                                    @AuthenticationPrincipal User currentAuthenticatedUser) {

        return eventoService.findByIdAndUpdate(eventoId, payload, currentAuthenticatedUser);
    }

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public void findByIdAndDelete(@PathVariable UUID eventoId,
                                  @AuthenticationPrincipal User currentAuthenticatedUser) {

        eventoService.delete(eventoId, currentAuthenticatedUser);
    }
}
