package it.adozioni.animali.Repository;

import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {

    Optional<EmailConfirmationToken> findByToken(String token);

    void deleteByAdottante(Adottante adottante);

    Optional<EmailConfirmationToken> findByAdottante(Adottante adottante);
}