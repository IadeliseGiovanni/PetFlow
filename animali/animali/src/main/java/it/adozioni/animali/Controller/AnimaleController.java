package it.adozioni.animali.Controller;

import it.adozioni.animali.Dto.AdozioneRequestDto;
import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.Animale;
import it.adozioni.animali.Service.AdottanteService;
import it.adozioni.animali.Service.AnimaleService;
import it.adozioni.animali.Service.DocumentoService;
import it.adozioni.animali.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animali")
@CrossOrigin("*")
public class AnimaleController {

    @Autowired
    private AnimaleService animaleService;
    @Autowired
    private AdottanteService adottanteService;
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/genera-contratto")
    public ResponseEntity<?> generaContratto(@RequestBody AdozioneRequestDto adozioneDto) {

        // 1. Recupero entità
        Animale animale = animaleService.findByIdEntity(adozioneDto.getIdAnimale());
        Adottante adottante = adottanteService.findByIdEntity(adozioneDto.getIdAdottante());

        if (animale == null || adottante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Errore: Dati non trovati.");
        }

        try {
            // 2. Generazione del PDF Professionale (Verde Salvia)
            byte[] pdf = documentoService.creaPdf(animale, adottante);

            // 3. INVIO EMAIL ALL'ADOTTANTE (Con il PDF allegato)
            emailService.inviaContrattoConAllegato(adottante.getEmail(), animale.getNome(), pdf);

            // 4. INVIO EMAIL DI RICEZIONE/CONGRATULAZIONI AL CENTRO
            // Notifica lo staff che la procedura digitale è andata a buon fine
            emailService.inviaNotificaRicezioneAlCentro(adottante.getEmail(), animale.getNome());

            // 5. RITORNO DEL PDF PER IL DOWNLOAD SU POSTMAN/BROWSER
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String fileName = "Contratto_" + animale.getNome() + ".pdf";
            headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nel processo di adozione.");
        }
    }
}