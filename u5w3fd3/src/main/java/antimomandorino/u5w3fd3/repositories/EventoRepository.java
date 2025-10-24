package antimomandorino.u5w3fd3.repositories;

import antimomandorino.u5w3fd3.entities.Evento;
import antimomandorino.u5w3fd3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {

    List<Evento> findByOrganizzatore(User organizzatore);
}
