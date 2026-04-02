package it.adozioni.animali.AnimaliTest;

import it.adozioni.animali.Dto.AnimaleDto;
import it.adozioni.animali.Mapper.AnimaleMapper;
import it.adozioni.animali.Model.Animale;
import it.adozioni.animali.Repository.AnimaleRepository;
import it.adozioni.animali.Service.AnimaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimaleServiceTest {

    @Mock
    private AnimaleRepository animaleRepository;

    @Mock
    private AnimaleMapper animaleMapper;

    @InjectMocks
    private AnimaleService animaleService;

    // 🔹 TEST 1 - findByIdEntity
    @Test
    void testFindByIdEntity() {
        // GIVEN
        Integer id = 1;
        Animale animale = new Animale();
        animale.setNome("Cane");

        when(animaleRepository.findById(id))
                .thenReturn(Optional.of(animale));

        // WHEN
        Animale result = animaleService.findByIdEntity(id);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("Cane");
        verify(animaleRepository).findById(id);
    }

    // 🔹 TEST 2 - findByNome
    @Test
    void testFindByNome() {
        // GIVEN
        String nome = "Gatto";

        List<Animale> listaEntity = List.of(new Animale());
        List<AnimaleDto> listaDto = List.of(new AnimaleDto());

        when(animaleRepository.findByNome(nome)).thenReturn(listaEntity);
        when(animaleMapper.toDTOList(listaEntity)).thenReturn(listaDto);

        // WHEN
        List<AnimaleDto> result = animaleService.findByNome(nome);

        // THEN
        assertThat(result).hasSize(1);
        verify(animaleRepository).findByNome(nome);
        verify(animaleMapper).toDTOList(listaEntity);
    }

    // 🔹 TEST 3 - findAll
    @Test
    void testFindAll() {
        // GIVEN
        List<Animale> listaEntity = List.of(new Animale(), new Animale());
        List<AnimaleDto> listaDto = List.of(new AnimaleDto(), new AnimaleDto());

        when(animaleRepository.findAll()).thenReturn(listaEntity);
        when(animaleMapper.toDTOList(listaEntity)).thenReturn(listaDto);

        // WHEN
        List<AnimaleDto> result = animaleService.findAll();

        // THEN
        assertThat(result).hasSize(2);
        verify(animaleRepository).findAll();
        verify(animaleMapper).toDTOList(listaEntity);
    }
}
