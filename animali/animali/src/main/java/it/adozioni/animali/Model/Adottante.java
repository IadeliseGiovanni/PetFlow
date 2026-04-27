package it.adozioni.animali.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adottante", schema = "public")
public class Adottante implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;
    private Boolean isSchedato;

    private LocalDate dataDiNascita;

    private String codiceFiscale;

    private String resetToken;
    private LocalDateTime resetTokenExpiration;

    private String indirizzo;

    @Column(nullable = false)
    private String password;

    private String ruolo = "USER";

    private boolean enabled = false;
    private String verificationToken;

    @OneToMany(mappedBy = "adottante")
    private List<Animale> animaliAdottati;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.ruolo == null || this.ruolo.isEmpty()) {
            return List.of();
        }
        String r = this.ruolo.toUpperCase();
        if (!r.startsWith("ROLE_")) r = "ROLE_" + r;
        return List.of(new SimpleGrantedAuthority(r));
    }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.enabled; }

    public enum StatoIdoneita {
        NON_RICHIESTA, IN_ATTESA, IDONEO, NON_IDONEO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_idoneita")
    private StatoIdoneita statoIdoneita = StatoIdoneita.NON_RICHIESTA;
}