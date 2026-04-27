package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.AnimaleDto;
import it.adozioni.animali.Dto.VisitaMedicaDto;
import it.adozioni.animali.Mapper.VisitaMedicaMapper;
import it.adozioni.animali.Model.VisitaMedica;
import it.adozioni.animali.Repository.VisitaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitaMedicaService extends AbstractService<VisitaMedica, VisitaMedicaDto> {

    private final VisitaMedicaRepository visitaMedicaRepository;
    private final VisitaMedicaMapper visitaMedicaMapper;

    @Autowired
    public VisitaMedicaService(VisitaMedicaRepository repository,
                               VisitaMedicaMapper mapper) {
        super(repository, mapper);
        this.visitaMedicaRepository = repository;
        this.visitaMedicaMapper = mapper;
    }

    public List<VisitaMedicaDto> getAll(){
        return visitaMedicaMapper.toDTOList(repository.findAll());
    }

    @Override
    public List<AnimaleDto> findAll() {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<VisitaMedicaDto> findAllVisite() {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findAll());
    }


    public List<VisitaMedicaDto> findByData(LocalDateTime data) {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findByData(data));
    }

    public List<VisitaMedicaDto> findByEsito(String esito) {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findByEsito(esito));
    }

    public List<VisitaMedicaDto> findByVeterinario(String veterinario) {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findByVeterinario(veterinario));
    }

    public List<VisitaMedicaDto> findByDataAndVeterinario(LocalDateTime data, String veterinario) {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findByDataAndVeterinario(data, veterinario));
    }

    public List<VisitaMedicaDto> findByDataAndEsito(LocalDateTime data, String esito) {
        return visitaMedicaMapper.toDTOList(visitaMedicaRepository.findByDataAndEsito(data, esito));
    }
}