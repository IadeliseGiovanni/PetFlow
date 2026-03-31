package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.AnimaleDto;
import it.adozioni.animali.Mapper.AdottanteMapper;
import it.adozioni.animali.Mapper.AnimaleMapper;
import it.adozioni.animali.Mapper.Converter;
import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.Animale;
import it.adozioni.animali.Repository.AdottanteRepository;
import it.adozioni.animali.Repository.AnimaleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AnimaleService extends AbstractService<Animale, AnimaleDto>{

     final private AnimaleMapper animaleMapper;
     final private AnimaleRepository animaleRepository;
     final private AdottanteRepository adottanteRepository;
     final private AdottanteMapper adottanteMapper;

    protected AnimaleService(JpaRepository<Animale, Integer> repository, Converter<Animale, AnimaleDto> converter, AnimaleMapper animaleMapper, AnimaleRepository animaleRepository, AdottanteRepository adottanteRepository, AdottanteMapper adottanteMapper) {
        super(repository, converter);

        this.animaleMapper = animaleMapper;
        this.animaleRepository = animaleRepository;
        this.adottanteRepository = adottanteRepository;
        this.adottanteMapper = adottanteMapper;
    }

    public String generaContrattoAdozione(Integer animaleId, Integer adottanteId) {
        // 1. Recupero i dati
        Animale animale = animaleRepository.findById(animaleId)
                .orElseThrow(() -> new RuntimeException("Animale non trovato"));

        Adottante adottante = adottanteRepository.findById(adottanteId)
                .orElseThrow(() -> new RuntimeException("Adottante non trovato"));

        // 2. Logica di business: aggiorno l'animale
        animale.setAdottato(true);
        animale.setAdottante(adottante);
        animaleRepository.save(animale);

        // 3. Restituzione del contratto formattato
        return String.format(
                "CERTIFICATO DI ADOZIONE\n" +
                        "--------------------------\n" +
                        "Il sottoscritto %s %s, residente a %s,\n" +
                        "si impegna a prendersi cura dell'animale descritto di seguito.\n" +
                        "Testo Plain: L'animale dovrà essere trattato con cura e rispetto.\n\n" +
                        "DATI ANIMALE:\n" +
                        "Nome: %s\n" +
                        "Microchip: %s\n\n" +
                        "Firma dell'adottante: ________________\n" +
                        "(Firmato digitalmente da %s %s)",
                adottante.getNome(), adottante.getCognome(),
                animale.getNome(), animale.getMicrochip(),
                adottante.getNome(), adottante.getCognome()
        );
    }
}
