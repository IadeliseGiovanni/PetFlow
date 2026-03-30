package it.adozioni.animali.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdottanteDto {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private Boolean isSchedato;
}