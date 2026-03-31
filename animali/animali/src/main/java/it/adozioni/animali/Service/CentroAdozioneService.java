package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Mapper.CentroAdozioneMapper;
import it.adozioni.animali.Model.CentroAdozione;
import it.adozioni.animali.Repository.CentroAdozioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // 1 JPA AUTO X CITTA
    public List<CentroAdozioneDto> findByCitta(String citta) {
        List<CentroAdozione> list = repository.findByCitta(citta);
        if (list == null || list.isEmpty()) return new ArrayList<>();
        return mapper.toDTOList(list);
    }

    // 2 JPA AUTO X NOPROFIT
    public List<CentroAdozioneDto> findByIsNoProfit(boolean noProfit) {
        List<CentroAdozione> list = repository.findByIsNoProfit(noProfit);
        if (list == null || list.isEmpty()) return new ArrayList<>();
        return mapper.toDTOList(list);
    }

    // 3 JPA AUTO X NOME CENTRO
    public CentroAdozioneDto findByNomeCentro(String nome) {
        // Se la repository restituisce una lista (come nello screenshot precedente)
        List<CentroAdozione> entities = repository.findByNomeCentro(nome);
        if (entities == null || entities.isEmpty()) return null;
        return mapper.toDTO(entities.get(0));
    }

    // 4 LISTA TUTTI I CENTRI (MAPPATI DTO)
    public List<CentroAdozioneDto> listaTuttiICentri() {
        return mapper.toDTOList(repository.findAll());
    }

    // 5 METODO PER ENTITY (UTILE PER TEST O LOGICA INTERNA)
    public CentroAdozione findByIdEntity(Integer id) {
        return repository.findById(id).orElse(null);
    }
}