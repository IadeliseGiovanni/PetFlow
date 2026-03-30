package it.adozioni.animali.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "centri_adozione", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class CentroAdozione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Sistemata la maiuscola e il tipo

    @Column(name = "nome_centro", nullable = false, length = 100)
    private String nomeCentro;

    private String indirizzo;
    private String citta;

    @Column(name = "capacita_massima")
    private Integer capacitaMassima;

    @Column(name = "is_no_profit")
    private Boolean isNoProfit;

    @OneToMany(mappedBy = "centro", cascade = CascadeType.ALL)
    private List<Animale> animaliOspitati;
}