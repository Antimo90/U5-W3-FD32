package antimomandorino.u5w3fd3.services;

import antimomandorino.u5w3fd3.entities.Evento;
import antimomandorino.u5w3fd3.entities.Prenotazione;
import antimomandorino.u5w3fd3.entities.StatoPrenotazione;
import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.exceptions.BadRequestException;
import antimomandorino.u5w3fd3.exceptions.NotFoundException;
import antimomandorino.u5w3fd3.exceptions.UnauthorizedException;
import antimomandorino.u5w3fd3.payloads.PrenotazioneDTO;
import antimomandorino.u5w3fd3.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    public Prenotazione findById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con ID " + id + " non trovata!"));
    }

    public Prenotazione save(PrenotazioneDTO payload, User utente) {
        Evento evento = eventoService.findById(payload.eventoId());

        // A. Controllo se l'utente ha già una prenotazione confermata per questo evento
        if (prenotazioneRepository.findByUtenteAndEvento(utente, evento).isPresent()) {
            throw new BadRequestException("Hai già una prenotazione per questo evento.");
        }

        // B. Controllo posti
        if (evento.getPostiDisponibili() <= 0) {
            throw new BadRequestException("Posti esauriti per l'evento: " + evento.getTitolo());
        }

        // C. Aggiorna posti disponibili
        eventoService.updatePostiDisponibili(evento, -1);

        // D. Crea e salva la prenotazione
        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setDataPrenotazione(LocalDateTime.now());
        nuovaPrenotazione.setStato(StatoPrenotazione.CONFERMATA); // Assumo l'Enum
        nuovaPrenotazione.setUtente(utente);
        nuovaPrenotazione.setEvento(evento);

        return prenotazioneRepository.save(nuovaPrenotazione);
    }


    public void delete(UUID prenotazioneId, User currentUser) {
        Prenotazione prenotazione = findById(prenotazioneId);


        if (!prenotazione.getUtente().getUserId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException("Non sei autorizzato ad annullare questa prenotazione.");
        }


        if (prenotazione.getStato() == StatoPrenotazione.ANNULLATA) {
            throw new BadRequestException("Questa prenotazione è già annullata.");
        }


        eventoService.updatePostiDisponibili(prenotazione.getEvento(), 1);
        prenotazione.setStato(StatoPrenotazione.ANNULLATA);
        prenotazioneRepository.save(prenotazione);


    }

    //EXTRA
    public List<Prenotazione> findMyReservations(User utente) {
        return prenotazioneRepository.findByUtente(utente);
    }
}
