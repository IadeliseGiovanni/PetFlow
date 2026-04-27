package it.adozioni.animali.Repository;

import it.adozioni.animali.Model.Animale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimaleRepository extends JpaRepository<Animale,Long> {
    List<Animale> findByNome(String nome);
    List<Animale>findByRazza(String razza);
    List<Animale>findByGenere(String genere);
    List<Animale>findBySpecie(String specie);
    List<Animale> findByAdottatoFalse();

    List<Animale> findBySpecieAndGenereAndAdottatoFalse(String specie, String genere);

    List<Animale> findBySpecieAndAdottatoFalse(String specie);

    List<Animale> findByGenereAndAdottatoFalse(String genere);
    List<Animale>findByRazzaAndSpecie(String razza, String specie);

    @Query("SELECT a FROM Animale a WHERE a.centroAdozione.id = :centroId AND a.adottato = false")
    List<Animale> findDisponibiliPerCentro(@Param("centroId") Long centroId);

   @Query("SELECT COUNT(a) FROM Animale a WHERE a.specie = :specie AND a.centroAdozione.id = :centroId")
   long countPerSpecieEIdCentro(@Param("specie") String specie, @Param("centroId") Long centroId);

    @Query(value = "SELECT * FROM animale ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Animale> findLastFiveAdded();

    @Query(value = "SELECT a.* FROM animale a " +
            "JOIN visita_medica v ON a.id = v.animale_id " +
            "GROUP BY a.id HAVING COUNT(v.id) >= :minVisite", nativeQuery = true)
    List<Animale> findAnimaliConMolteVisite(@Param("minVisite") int minVisite);

    List<Animale> findBySpecieAndGenere(String specie, String genere);

    List<Animale> findByCentroAdozioneId(Long centroId);

    List<Animale> findBySpecieAndGenereAndCentroAdozioneId(String specie, String genere, Long centroId);
    List<Animale> findBySpecieAndCentroAdozioneId(String specie, Long centroId);
    List<Animale> findByGenereAndCentroAdozioneId(String genere, Long centroId);

    @Query("SELECT a FROM Animale a WHERE " +
            "(:specie IS NULL OR a.specie = :specie) AND " +
            "(:genere IS NULL OR a.genere = :genere) AND " +
            "(:centroId IS NULL OR a.centroAdozione.id = :centroId)")
    List<Animale> findFiltered(@Param("specie") String specie,
                               @Param("genere") String genere,
                               @Param("centroId") Long centroId);
}
