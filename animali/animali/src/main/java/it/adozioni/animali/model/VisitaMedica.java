package it.adozioni.animali.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VisitaMedica", schema = "Animali")
public class VisitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;

    LocalDateTime data;

    String esito;

    String veterinario;

    @ManyToOne
    @JoinColumn(name = "animale_id", nullable = true)
    private Animale animale;

}
