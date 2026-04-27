package it.adozioni.animali.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO ottimizzato per la generazione del contratto PDF.
 * Le annotazioni @JsonProperty assicurano che Spring legga correttamente il JSON da Postman.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdozioneRequestDto {

    @JsonProperty("idAnimale")
    private Long idAnimale;

    @JsonProperty("idAdottante")
    private Long idAdottante;

    @JsonProperty("residenza")
    private String residenza;

    @JsonProperty("noteAggiuntive")
    private String noteAggiuntive;
    //
}