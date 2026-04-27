package it.adozioni.animali.Dto;

import it.adozioni.animali.Model.StatoPratica;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PraticaAdozioneDto {
    private Long id;
    private Long adottanteId;
    private String adottanteNominativo; // Nome + Cognome uniti
    private Long animaleId;
    private String animaleNome;
    private StatoPratica stato;
    private LocalDateTime dataApertura;
    private String noteAdmin;
}