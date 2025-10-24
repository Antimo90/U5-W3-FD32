package antimomandorino.u5w3fd3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@NoArgsConstructor
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Evento {

    @Id
    @GeneratedValue
    @Column(name = "evento_id")
    private UUID eventoId;
    private String titolo;
    private String descrizione;
    private LocalDateTime data;
    private String luogo;
    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id", nullable = false) // Chiave Esterna
    private User organizzatore;


    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;

    public Evento(String titolo, String descrizione, LocalDateTime data, String luogo, int postiDisponibili, User organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        this.organizzatore = organizzatore;

    }

    public UUID getEventoId() {
        return eventoId;
    }


    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public User getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(User organizzatore) {
        this.organizzatore = organizzatore;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "eventoId=" + eventoId +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", luogo='" + luogo + '\'' +
                ", postiDisponibili=" + postiDisponibili +

                '}';
    }
}
