package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Mapper.CentroAdozioneMapper;
import it.adozioni.animali.Model.CentroAdozione;
import it.adozioni.animali.Repository.CentroAdozioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CentroAdozioneService extends AbstractService<CentroAdozione, CentroAdozioneDto> {

    @Autowired
    public CentroAdozioneService(CentroAdozioneRepository repository, CentroAdozioneMapper mapper) {
        super(repository, mapper);
    }

    // Metodo extra se vuoi chiamarlo specificamente dal Controller
    public Iterable<CentroAdozioneDto> listaTuttiICentri() {
        return getAll();
    }
}