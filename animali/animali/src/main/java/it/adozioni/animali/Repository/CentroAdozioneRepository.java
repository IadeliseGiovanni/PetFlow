package it.adozioni.animali.Repository;

import it.adozioni.animali.Model.CentroAdozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CentroAdozioneRepository extends JpaRepository<CentroAdozione, Long> {

    List<CentroAdozione> findByNomeCentro(String nomeCentro);

    List<CentroAdozione> findByCitta(String citta);

    CentroAdozione findByIndirizzo(String indirizzo);

    List<CentroAdozione> findByIsNoProfit(Boolean isNoProfit);

    CentroAdozione findByNomeCentroAndCitta(String nomeCentro, String citta);

    @Query("SELECT c FROM CentroAdozione c WHERE c.nomeCentro = ?1 AND c.citta = ?2")
    List<CentroAdozione> findByNomeAndCittaQuery(String nome, String citta);

    @Query("SELECT c FROM CentroAdozione c WHERE c.isNoProfit = true")
    List<CentroAdozione> findAllNoProfit();

    @Query(value = "SELECT * FROM centro_adozione c WHERE c.indirizzo = ?1", nativeQuery = true)
    CentroAdozione findByIndirizzoNative(String indirizzo);

    @Query(value = "SELECT * FROM centro_adozione c WHERE c.citta = ?1", nativeQuery = true)
    List<CentroAdozione> findByCittaNative(String citta);

    List<CentroAdozione> findByNomeCentroContaining(String keyword);

}