package it.adozioni.animali.Repository;

import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.Volontario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolontarioRepository extends JpaRepository<Volontario, Long> {

    List<Volontario> findByNome(String nome);

    List<Volontario> findByCognome(String cognome);

    Volontario findByCf(String cf);

    List<Volontario> findByTurno(String turno);

    Volontario findByNomeAndCognome(String nome, String cognome);

    @Query("SELECT v FROM Volontario v WHERE v.nome = ?1 AND v.turno = ?2")
    List<Volontario> findByNomeAndTurno(String nome, String turno);

    @Query("SELECT v FROM Volontario v WHERE v.cognome = ?1")
    List<Volontario> findByCognomeQuery(String cognome);

    @Query(value = "SELECT * FROM Volontario v WHERE v.cf = ?1", nativeQuery = true)
    Volontario findByCfNative(String cf);

    @Query(value = "SELECT * FROM Volontario v WHERE v.turno = ?1", nativeQuery = true)
    List<Volontario> findByTurnoNative(String turno);

    List<Volontario> findByNomeContaining(String keyword);

    Optional<Volontario> findByEmail(String email);

    boolean existsByEmail(String email);
}
