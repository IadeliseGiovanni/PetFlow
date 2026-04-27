package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.AdottanteDto;
import it.adozioni.animali.Dto.AnimaleDto;
import it.adozioni.animali.Mapper.AdottanteMapper;
import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.EmailConfirmationToken;
import it.adozioni.animali.Repository.AdottanteRepository;
import it.adozioni.animali.Repository.EmailConfirmationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdottanteService extends AbstractService<Adottante, AdottanteDto> implements UserDetailsService {

    private final AdottanteRepository adottanteRepository;
    private final AdottanteMapper adottanteMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EmailConfirmationTokenRepository tokenRepository;

    @Autowired
    public AdottanteService(AdottanteRepository adottanteRepository,
                            AdottanteMapper adottanteMapper,
                            @Lazy PasswordEncoder passwordEncoder,
                            EmailService emailService, EmailConfirmationTokenRepository tokenRepository) {
        super(adottanteRepository, adottanteMapper);
        this.adottanteRepository = adottanteRepository;
        this.adottanteMapper = adottanteMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public AdottanteDto registra(AdottanteDto dto) {
        if (adottanteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email già registrata!");
        }

        Adottante entity = adottanteMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRuolo("USER");
        entity.setEnabled(false);
        entity.setIsSchedato(false);

        String token = UUID.randomUUID().toString();
        entity.setVerificationToken(token);

        Adottante salvato = adottanteRepository.save(entity);

        this.inviaEmailVerifica(salvato.getEmail(), token);

        return adottanteMapper.toDTO(salvato);
    }


    @Transactional
    public boolean verifyToken(String token) {
        return adottanteRepository.findByVerificationToken(token)
                .map(utente -> {
                    utente.setEnabled(true);
                    utente.setVerificationToken(null);
                    adottanteRepository.save(utente);
                    return true;
                }).orElse(false);
    }

    public Adottante findByEmailEntity(String email) {
        return adottanteRepository.findByEmail(email).orElse(null);
    }

    public void inviaEmailVerifica(String email, String token) {
        emailService.sendVerificationEmail(email, token);
    }

    public Adottante findByIdEntity(Long id) {
        if (id == null) return null;
        return adottanteRepository.findById(id).orElse(null);
    }

    @Override
    public List<AnimaleDto> findAll() {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<AdottanteDto> findAllAdottantiTrue() {
        return adottanteMapper.toDTOList(adottanteRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adottanteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + email));
    }

    @Transactional
    public void aggiornaIdoneita(Long id, boolean stato) {
        adottanteRepository.findById(id).ifPresent(a -> {

            a.setIsSchedato(stato);

            if (stato) {
                a.setStatoIdoneita(Adottante.StatoIdoneita.IDONEO);
            } else {
                a.setStatoIdoneita(Adottante.StatoIdoneita.NON_RICHIESTA);
            }

            adottanteRepository.save(a);
        });
    }

    @Transactional
    public void aggiornaRuolo(Long id, String nuovoRuolo) {
        adottanteRepository.findById(id).ifPresent(a -> {
            a.setRuolo(nuovoRuolo);
            adottanteRepository.save(a);
        });
    }

    public List<AdottanteDto> findByCognome(String cognome) {
        return adottanteMapper.toDTOList(adottanteRepository.findByCognome(cognome));
    }

    @Transactional(readOnly = true)
    public AdottanteDto getMioProfilo(String email) {
        Adottante entity = adottanteRepository.findByEmailWithAnimals(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return adottanteMapper.toDTO(entity);
    }

    @Transactional
    public AdottanteDto patch(Long id, AdottanteDto dto) {
        Adottante adottante = adottanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adottante non trovato con ID: " + id));

        if (dto.getNome() != null) adottante.setNome(dto.getNome());
        if (dto.getCognome() != null) adottante.setCognome(dto.getCognome());
        if (dto.getDataDiNascita() != null) adottante.setDataDiNascita(dto.getDataDiNascita());
        if (dto.getIndirizzo() != null) adottante.setIndirizzo(dto.getIndirizzo());
        if (dto.getTelefono() != null) adottante.setTelefono(dto.getTelefono());
        if (dto.getCodiceFiscale() != null) adottante.setCodiceFiscale(dto.getCodiceFiscale());

        Adottante salvato = adottanteRepository.save(adottante);
        return adottanteMapper.toDTO(salvato);
    }

    @Transactional
    public void avviaPraticaIdoneita(Long adottanteId) {
        Adottante adottante = adottanteRepository.findById(adottanteId)
                .orElseThrow(() -> new RuntimeException("Adottante non trovato"));

        if (adottante.getStatoIdoneita() != null &&
                adottante.getStatoIdoneita() != Adottante.StatoIdoneita.NON_RICHIESTA) {
            throw new RuntimeException("Esiste già una pratica in corso o sei già idoneo.");
        }

        adottante.setStatoIdoneita(Adottante.StatoIdoneita.IN_ATTESA);
        adottanteRepository.save(adottante);

        emailService.inviaConfermaRichiestaIdoneita(adottante.getEmail(), adottante.getNome());
    }

    @Transactional
    public String richiediCambioEmail(Long adottanteId, String nuovaEmail) {
        Adottante adottante = adottanteRepository.findById(adottanteId)
                .orElseThrow(() -> new RuntimeException("Adottante non trovato"));

        tokenRepository.findByAdottante(adottante).ifPresent(token -> {
            tokenRepository.delete(token);
            tokenRepository.flush();
        });

        String stringToken = UUID.randomUUID().toString();
        EmailConfirmationToken newToken = new EmailConfirmationToken();
        newToken.setToken(stringToken);
        newToken.setNuovaEmail(nuovaEmail);
        newToken.setAdottante(adottante);
        newToken.setDataScadenza(LocalDateTime.now().plusHours(24));

        tokenRepository.save(newToken);

        String linkConferma = "http://localhost:8080/api/Adottante/conferma-email?token=" + stringToken;
        emailService.inviaMailConferma(nuovaEmail, linkConferma);

        return "Email inviata con successo!";
    }

    @Transactional
    public void confermaCambioEmail(String token) {
        EmailConfirmationToken confermaToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token non valido o già utilizzato."));

        Adottante adottante = confermaToken.getAdottante();
        String vecchiaEmail = adottante.getEmail();
        String nuovaEmail = confermaToken.getNuovaEmail();

        adottante.setEmail(nuovaEmail);

        adottanteRepository.saveAndFlush(adottante);

        tokenRepository.delete(confermaToken);
        tokenRepository.flush();

        try {
            emailService.inviaNotificaCambioEffettuato(vecchiaEmail, nuovaEmail);
        } catch (Exception e) {
            System.err.println("Notifica di sicurezza fallita: " + e.getMessage());
        }
    }

    @Transactional
    public void cambiaPassword(Long adottanteId, String vecchiaPassword, String nuovaPassword) {
        Adottante adottante = adottanteRepository.findById(adottanteId)
                .orElseThrow(() -> new RuntimeException("Adottante non trovato"));

        if (!passwordEncoder.matches(vecchiaPassword, adottante.getPassword())) {
            throw new RuntimeException("La password attuale non è corretta");
        }
        adottante.setPassword(passwordEncoder.encode(nuovaPassword));

        adottanteRepository.saveAndFlush(adottante);

        emailService.inviaConfermaCambioPassword(adottante.getEmail());
    }

    @Transactional
    public void salvaResetToken(Long id, String token) {
        Adottante adottante = adottanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        adottante.setResetToken(token);
        adottante.setResetTokenExpiration(LocalDateTime.now().plusMinutes(30));

        adottanteRepository.saveAndFlush(adottante);
    }

    @Transactional
    public boolean aggiornaPasswordConToken(String token, String nuovaPassword) {
        String cleanToken = token.trim();
        System.out.println("DEBUG: Ricerca nel DB per token: [" + cleanToken + "]");

        Optional<Adottante> opzionale = adottanteRepository.findByResetToken(cleanToken);

        if (opzionale.isPresent()) {
            Adottante adottante = opzionale.get();
            System.out.println("DEBUG: Utente trovato: " + adottante.getEmail());

            if (adottante.getResetTokenExpiration().isAfter(LocalDateTime.now())) {
                adottante.setPassword(passwordEncoder.encode(nuovaPassword));

                adottante.setResetToken(null);
                adottante.setResetTokenExpiration(null);

                adottanteRepository.saveAndFlush(adottante);
                return true;
            }
            System.out.println("DEBUG: Token scaduto il: " + adottante.getResetTokenExpiration());
        } else {
            System.out.println("DEBUG: Token non trovato nel database.");
        }
        return false;
    }
}