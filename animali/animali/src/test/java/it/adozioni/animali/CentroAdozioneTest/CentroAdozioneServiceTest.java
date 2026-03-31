package it.adozioni.animali.CentroAdozioneTest;

import it.adozioni.animali.Dto.CentroAdozioneDto;
import it.adozioni.animali.Mapper.CentroAdozioneMapper;
import it.adozioni.animali.Model.CentroAdozione;
import it.adozioni.animali.Repository.CentroAdozioneRepository;
import it.adozioni.animali.Service.CentroAdozioneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// Questa riga risolve l'errore "UnnecessaryStubbingException" rendendo Mockito più flessibile (come nei droni)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CentroAdozioneServiceTest {

    @Mock
    private CentroAdozioneRepository repository;

    @Mock
    private CentroAdozioneMapper mapper;

    @InjectMocks
    private CentroAdozioneService service;

    @Test
    public void testReadById() {
        // GIVEN
        Integer id = 1;
        CentroAdozione entity = new CentroAdozione();
        entity.setId(id);
        CentroAdozioneDto dto = new CentroAdozioneDto();
        dto.setId(id);

        // Configuriamo il mock come nei droni
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(any(CentroAdozione.class))).thenReturn(dto);

        // WHEN - Usiamo il metodo del padre (AbstractService) che restituisce DTO
        CentroAdozioneDto result = service.read(id);;

        // THEN
        assertNotNull(result, "Il risultato non deve essere null");
        assertEquals(id, result.getId());
    }

    @Test
    public void testFindByNomeCentroSuccess() {
        // GIVEN
        String nome = "RifugioSperanza";
        CentroAdozione entity = new CentroAdozione();
        CentroAdozioneDto dto = new CentroAdozioneDto();

        // Se la repository restituisce una lista (come nel tuo Service attuale)
        when(repository.findByNomeCentro(nome)).thenReturn(List.of(entity));
        when(mapper.toDTO(any(CentroAdozione.class))).thenReturn(dto);

        // WHEN
        CentroAdozioneDto result = service.findByNomeCentro(nome);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testFindByCitta() {
        // GIVEN
        String citta = "Roma";
        when(repository.findByCitta(citta)).thenReturn(List.of(new CentroAdozione()));
        when(mapper.toDTOList(any())).thenReturn(List.of(new CentroAdozioneDto()));

        // WHEN
        List<CentroAdozioneDto> result = service.findByCitta(citta);

        // THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindAll() {
        // GIVEN
        when(repository.findAll()).thenReturn(List.of(new CentroAdozione(), new CentroAdozione()));
        when(mapper.toDTOList(any())).thenReturn(List.of(new CentroAdozioneDto(), new CentroAdozioneDto()));

        // WHEN
        List<CentroAdozioneDto> result = service.listaTuttiICentri();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteCentro() {
        // WHEN
        service.delete(1);

        // THEN
        verify(repository, times(1)).deleteById(1);
    }
}