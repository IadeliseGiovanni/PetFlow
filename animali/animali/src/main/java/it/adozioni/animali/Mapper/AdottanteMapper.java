package it.adozioni.animali.Mapper;

import it.adozioni.animali.Dto.AdottanteDto;
import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Model.Adottante;
import it.adozioni.animali.Model.CentroAdozione;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdottanteMapper extends AbstractConverter<Adottante, AdottanteDto> {

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public AdottanteDto toDTO(Adottante entity) {
        return mapper.map(entity, AdottanteDto.class);
    }

    @Override
    public Adottante toEntity(AdottanteDto dto) {
        return mapper.map(dto, Adottante.class);
    }

}