package it.adozioni.animali.VisitaMedicaTest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import it.adozioni.animali.Dto.VisitaMedicaDto;
import it.adozioni.animali.Mapper.VisitaMedicaMapper;
import it.adozioni.animali.Model.VisitaMedica;
import it.adozioni.animali.Repository.VisitaMedicaRepository;
import it.adozioni.animali.Service.VisitaMedicaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class) // Abilita l'automatismo di Mockito
public class VisitaMedicaServiceTest {

    @Mock
    private VisitaMedicaRepository repository; // crea il "finto" repo

    @Mock
    private VisitaMedicaMapper mapper; // crea il "finto" mapper

    @InjectMocks
    private VisitaMedicaService service; // crea il service e ci inietta i mock sopra

    @Test
    void testFindByData() {
        LocalDateTime data = LocalDateTime.now();
        List<VisitaMedica> entities = List.of(new VisitaMedica());
        List<VisitaMedicaDto> dtos = List.of(new VisitaMedicaDto());

        when(repository.findByData(data)).thenReturn(entities);
        when(mapper.toDTOList(entities)).thenReturn(dtos);

        List<VisitaMedicaDto> result = service.findByData(data);

        assertThat(result).hasSize(1).isEqualTo(dtos);
    }

    @Test
    void testFindByEsito() {
        String esito = "Positivo";
        when(repository.findByEsito(esito)).thenReturn(List.of());
        when(mapper.toDTOList(anyList())).thenReturn(List.of());

        List<VisitaMedicaDto> result = service.findByEsito(esito);

        assertThat(result).isEmpty();
        verify(repository).findByEsito(esito);
    }

    @Test
    void testFindByDataAndVeterinario() {
        LocalDateTime data = LocalDateTime.now();
        String vet = "Dr. Rossi";
        List<VisitaMedicaDto> dtos = List.of(new VisitaMedicaDto());

        when(repository.findByDataAndVeterinario(data, vet)).thenReturn(List.of());
        when(mapper.toDTOList(any())).thenReturn(dtos);

        List<VisitaMedicaDto> result = service.findByDataAndVeterinario(data, vet);

        assertThat(result).isEqualTo(dtos);
    }

    @Test
    void testFindByVeterinario() {
        String vet = "Dr. House";
        List<VisitaMedica> entities = List.of(new VisitaMedica());
        List<VisitaMedicaDto> dtos = List.of(new VisitaMedicaDto());

        when(repository.findByVeterinario(vet)).thenReturn(entities);
        when(mapper.toDTOList(entities)).thenReturn(dtos);

        List<VisitaMedicaDto> result = service.findByVeterinario(vet);

        assertThat(result).hasSize(1).isEqualTo(dtos);
        verify(repository).findByVeterinario(vet);
    }

    @Test
    void testFindByDataAndEsito() {
        LocalDateTime data = LocalDateTime.now();
        String esito = "In attesa";
        List<VisitaMedica> entities = List.of(new VisitaMedica());
        List<VisitaMedicaDto> dtos = List.of(new VisitaMedicaDto());

        when(repository.findByDataAndEsito(data, esito)).thenReturn(entities);
        when(mapper.toDTOList(entities)).thenReturn(dtos);

        List<VisitaMedicaDto> result = service.findByDataAndEsito(data, esito);

        assertThat(result).hasSize(1);
        verify(mapper).toDTOList(entities);
    }
}
