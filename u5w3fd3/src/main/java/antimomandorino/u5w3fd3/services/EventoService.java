package antimomandorino.u5w3fd3.services;

import antimomandorino.u5w3fd3.entities.Evento;
import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.entities.UserRole;
import antimomandorino.u5w3fd3.exceptions.NotFoundException;
import antimomandorino.u5w3fd3.exceptions.UnauthorizedException;
import antimomandorino.u5w3fd3.payloads.EventoDTO;
import antimomandorino.u5w3fd3.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento findById(UUID id) {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento con ID " + id + " non trovato!"));
    }

    public Page<Evento> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.eventoRepository.findAll(pageable);
    }

    public Evento save(EventoDTO payload, User organizzatore) {

        if (organizzatore.getRuolo() != UserRole.ORGANIZZATORE) {
            throw new UnauthorizedException("Solo gli Organizzatori possono creare eventi.");
        }

        Evento nuovoEvento = new Evento();
        nuovoEvento.setTitolo(payload.titolo());
        nuovoEvento.setDescrizione(payload.descrizione());
        nuovoEvento.setData(payload.data());
        nuovoEvento.setLuogo(payload.luogo());
        nuovoEvento.setPostiDisponibili(payload.postiDisponibili());
        nuovoEvento.setOrganizzatore(organizzatore);

        return this.eventoRepository.save(nuovoEvento);
    }

    public Evento findByIdAndUpdate(UUID eventoId, EventoDTO payload, User currentUser) {
        Evento found = findById(eventoId);

        if (!found.getOrganizzatore().getUserId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo evento.");
        }

        found.setTitolo(payload.titolo());
        found.setDescrizione(payload.descrizione());
        found.setData(payload.data());
        found.setLuogo(payload.luogo());
        found.setPostiDisponibili(payload.postiDisponibili());

        return this.eventoRepository.save(found);
    }


    public void delete(UUID eventoId, User currentUser) {
        Evento existingEvent = findById(eventoId);


        if (!existingEvent.getOrganizzatore().getUserId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException("Non sei autorizzato ad eliminare questo evento.");
        }

        this.eventoRepository.delete(existingEvent);
    }


    public void updatePostiDisponibili(Evento evento, int change) {
        evento.setPostiDisponibili(evento.getPostiDisponibili() + change);
        this.eventoRepository.save(evento);
    }
}
