package antimomandorino.u5w3fd3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@NoArgsConstructor
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole ruolo;

    private String nome;
    private String cognome;

    @OneToMany(mappedBy = "organizzatore", cascade = CascadeType.ALL)
    private List<Evento> eventiOrganizzati;


    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;

    public User(String email, String password, String nome, String cognome, List<Evento> eventiOrganizzati, List<Prenotazione> prenotazioni) {
        this.email = email;
        this.password = password;
        this.ruolo = UserRole.NORMALE;
        this.nome = nome;
        this.cognome = cognome;
        this.eventiOrganizzati = eventiOrganizzati;
        this.prenotazioni = prenotazioni;
    }

    public UUID getUserId() {
        return userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRuolo() {
        return ruolo;
    }

    public void setRuolo(UserRole ruolo) {
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<Evento> getEventiOrganizzati() {
        return eventiOrganizzati;
    }

    public void setEventiOrganizzati(List<Evento> eventiOrganizzati) {
        this.eventiOrganizzati = eventiOrganizzati;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +

                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
