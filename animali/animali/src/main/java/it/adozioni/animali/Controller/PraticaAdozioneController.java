package it.adozioni.animali.Controller;

import it.adozioni.animali.Dto.PraticaAdozioneDto;
import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.StatoPratica;
import it.adozioni.animali.Repository.AdottanteRepository;
import it.adozioni.animali.Service.PraticaAdozioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/pratiche")
@CrossOrigin(origins = "*")
public class PraticaAdozioneController {

    @Autowired
    private PraticaAdozioneService praticaService;

    @Autowired
    private AdottanteRepository adottanteRepository;

    @PostMapping("/avvia/{animaleId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> avviaPratica(@PathVariable Long animaleId, Principal principal) {
        try {
            Adottante utenteLoggato = adottanteRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato nel sistema."));

            PraticaAdozioneDto nuovaPratica = praticaService.avviaPratica(utenteLoggato, animaleId);

            return new ResponseEntity<>(nuovaPratica, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Si è verificato un errore imprevisto durante l'avvio della pratica.");
        }
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PraticaAdozioneDto>> getAllPratiche() {
        return ResponseEntity.ok(praticaService.getAllPratiche());
    }

    @GetMapping("/mie-pratiche")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PraticaAdozioneDto>> getMiePratiche(Principal principal) {
        Adottante utente = adottanteRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return ResponseEntity.ok(praticaService.getPraticheByUtente(utente.getId()));
    }

    @PatchMapping("/admin/{id}/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cambiaStatoPratica(
            @PathVariable Long id,
            @RequestParam StatoPratica nuovoStato,
            @RequestBody(required = false) String note) {
        try {
            PraticaAdozioneDto aggiornata = praticaService.aggiornaStatoPratica(id, nuovoStato, note);
            return ResponseEntity.ok(aggiornata);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'aggiornamento della pratica.");
        }
    }


}