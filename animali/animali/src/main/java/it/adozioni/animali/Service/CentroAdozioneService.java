package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Mapper.CentroAdozioneMapper;
import it.adozioni.animali.Model.CentroAdozione;
import it.adozioni.animali.Repository.CentroAdozioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroAdozioneService extends AbstractService<CentroAdozione, CentroAdozioneDto> {

    private final CentroAdozioneRepository repository;
    private final CentroAdozioneMapper mapper;

    @Autowired
    public CentroAdozioneService(CentroAdozioneRepository repository, CentroAdozioneMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    // 1. JPA AUTO - Cerca per città (Restituisce Lista DTO)
    public List<CentroAdozioneDto> findByCitta(String citta) {
        return mapper.toDTOList(repository.findByCitta(citta));
    }

    // 2. JPA AUTO - Cerca per NoProfit (Restituisce Lista DTO)
    public List<CentroAdozioneDto> findByIsNoProfit(boolean noProfit) {
        return mapper.toDTOList(repository.findByIsNoProfit(noProfit));
    }

    // 3. JPA AUTO - Cerca per Nome Centro (Restituisce Singolo DTO)
    public CentroAdozioneDto findByNomeCentro(String nome) {
        CentroAdozione centro = (CentroAdozione) repository.findByNomeCentro(nome);
        if (centro == null) return null;
        return mapper.toDTO(centro);
    }

    // 4. Metodo per ottenere tutti i centri mappati in DTO
    public List<CentroAdozioneDto> listaTuttiICentri() {
        return mapper.toDTOList(repository.findAll());
    }

    // Nota: i metodi findAll() e findById(id) che restituiscono Entity
    // sono già ereditati da AbstractService, quindi non serve riscriverli
    // a meno che tu non voglia personalizzarli.
}