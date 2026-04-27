package it.adozioni.animali.Service;

import it.adozioni.animali.Dto.AnimaleDto;
import it.adozioni.animali.Mapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<ENTITY,DTO> implements ServiceDTO<DTO> {

    protected JpaRepository<ENTITY,Long> repository;
    protected Converter<ENTITY,DTO> converter;

    protected AbstractService(JpaRepository<ENTITY, Long> repository,
                              Converter<ENTITY, DTO> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public DTO insert(DTO dto) {
        ENTITY entity = converter.toEntity(dto);
        return converter.toDTO(repository.save(entity));
    }

    @Override
    public Iterable<DTO> getAll() {
        return converter.toDTOList(repository.findAll());
    }

    @Override
    public DTO read(Long id) {
        return converter.toDTO(repository.findById(id).get());
    }

    @Override
    public DTO update(DTO dto) {
        return converter.toDTO(repository.save(converter.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public abstract List<AnimaleDto> findAll();
}