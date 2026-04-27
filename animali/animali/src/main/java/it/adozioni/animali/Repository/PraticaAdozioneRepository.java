package it.adozioni.animali.Repository;

import it.adozioni.animali.Model.PraticaAdozione;
import it.adozioni.animali.Model.StatoPratica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PraticaAdozioneRepository extends JpaRepository<PraticaAdozione, Long> {
    List<PraticaAdozione> findByAdottanteId(Long adottanteId);

    // Utile per controllare se esiste già una pratica aperta per lo stesso animale dallo stesso utente
    boolean existsByAdottanteIdAndAnimaleIdAndStatoNot(Long adottanteId, Long animaleId, StatoPratica stato);


}