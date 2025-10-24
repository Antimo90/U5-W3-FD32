package antimomandorino.u5w3fd3.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Prenotazione {
    @Id
    @GeneratedValue
    @Column(name = "prenotazione_id")
    private UUID prenotazioneId;
    @Column(name = "data_prenotazione")
    private LocalDateTime dataPrenotazione;
    @Enumerated(EnumType.STRING)
    private StatoPrenotazione stato;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private User utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false) // Chiave Esterna
    private Evento evento;

    public Prenotazione(LocalDateTime dataPrenotazione, StatoPrenotazione stato, User utente, Evento evento) {
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
        this.utente = utente;
        this.evento = evento;
    }

    public UUID getPrenotazioneId() {
        return prenotazioneId;
    }


    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public void setStato(StatoPrenotazione stato) {
        this.stato = stato;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "prenotazioneId=" + prenotazioneId +
                ", dataPrenotazione=" + dataPrenotazione +
                ", stato=" + stato +

                '}';
    }
}
