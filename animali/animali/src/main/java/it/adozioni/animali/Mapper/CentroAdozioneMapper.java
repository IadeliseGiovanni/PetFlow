package it.adozioni.animali.Mapper;

import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Model.CentroAdozione;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class CentroAdozioneMapper extends AbstractConverter<CentroAdozione, CentroAdozioneDto>  {

    private final ModelMapper modelMapper = new ModelMapper();

    // Converte le liste (necessario per il Service)
    @Override
    public CentroAdozione toEntity(CentroAdozioneDto dto) {
        return modelMapper.map(dto,CentroAdozione.class);
    }

    @Override
    public CentroAdozioneDto toDTO(CentroAdozione entity) {
        return modelMapper.map(entity,CentroAdozioneDto.class);
    }

    }
