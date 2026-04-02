package it.adozioni.animali.VolontarioTest;

import it.adozioni.animali.Mapper.VolontarioMapper;
import it.adozioni.animali.Model.Volontario;
import it.adozioni.animali.Repository.VolontarioRepository;
import it.adozioni.animali.Service.VolontarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VolontarioServiceTest {

    @Mock
    private VolontarioRepository volontarioRepository;

    @Mock
    private VolontarioMapper volontarioMapper;

    @InjectMocks
    private VolontarioService volontarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 🔹 TEST 1 - Cerca per CF
    @Test
    void testFindByCf() {
        Volontario v = new Volontario();
        v.setCf("ABC123");

        when(volontarioRepository.findByCf("ABC123"))
                .thenReturn(v);

        Volontario result = volontarioService.cercaPerCf("ABC123");

        assertNotNull(result);
        assertEquals("ABC123", result.getCf());
    }

    // 🔹 TEST 2 - Cerca per nome
    @Test
    void testFindByNome() {
        Volontario v = new Volontario();
        v.setNome("Luca");

        when(volontarioRepository.findByNome("Luca"))
                .thenReturn(List.of(v));

        List<Volontario> result = volontarioService.cercaPerNome("Luca");

        assertFalse(result.isEmpty());
        assertEquals("Luca", result.get(0).getNome());
    }

    // 🔹 TEST 3 - Cerca per turno
    @Test
    void testFindByTurno() {
        Volontario v = new Volontario();
        v.setTurno("Mattina");

        when(volontarioRepository.findByTurno("Mattina"))
                .thenReturn(List.of(v));

        List<?> result = volontarioService.findByTurnoDto("Mattina");

        assertFalse(result.isEmpty());
    }
}
