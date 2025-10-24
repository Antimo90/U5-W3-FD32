package antimomandorino.u5w3fd3.repositories;

import antimomandorino.u5w3fd3.entities.Evento;
import antimomandorino.u5w3fd3.entities.Prenotazione;
import antimomandorino.u5w3fd3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    List<Prenotazione> findByUtente(User utente);

    Optional<Prenotazione> findByUtenteAndEvento(User utente, Evento evento);
}
