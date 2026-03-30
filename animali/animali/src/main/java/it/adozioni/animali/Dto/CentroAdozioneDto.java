package it.adozioni.animali.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CentroAdozioneDto {

    // 🔹 AGGIUNGI QUESTA RIGA QUI SOTTO
    private Long id;

    private String nomeCentro;

    private String indirizzo;

    private String citta;

    private Integer capacitaMassima;

    private Boolean isNoProfit;
}