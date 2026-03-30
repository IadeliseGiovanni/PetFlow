package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.VisitaMedicaDto;
import it.adozioni.animali.Mapper.AbstractConverter;
import it.adozioni.animali.Mapper.Converter;
import it.adozioni.animali.Mapper.VisitaMedicaMapper;
import it.adozioni.animali.Model.VisitaMedica;
import it.adozioni.animali.Repository.VisitaMedicaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class VisitaMedicaService extends AbstractService<VisitaMedica, VisitaMedicaDto> {

    private final VisitaMedicaRepository visitaMedicaRepository;

    private final VisitaMedicaMapper visitaMedicaMapper;

    protected VisitaMedicaService(JpaRepository<VisitaMedica, Integer> repository, Converter<VisitaMedica, VisitaMedicaDto> converter, VisitaMedicaMapper visitaMedicaMapper, VisitaMedicaRepository visitaMedicaRepository) {
        super(repository, converter);
        this.visitaMedicaMapper = visitaMedicaMapper;
        this.visitaMedicaRepository = visitaMedicaRepository;
    }


}
