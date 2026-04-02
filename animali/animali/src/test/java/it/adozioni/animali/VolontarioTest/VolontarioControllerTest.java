package it.adozioni.animali.VolontarioTest;

import it.adozioni.animali.Controller.VolontarioController;
import it.adozioni.animali.Dto.VolontarioDto;
import it.adozioni.animali.Service.VolontarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolontarioControllerTest {

    @Mock
    private VolontarioService service;

    @InjectMocks
    private VolontarioController controller;

    // 🔹 TEST 1 - findByNome
    @Test
    void testFindByNome() {
        // GIVEN
        String nome = "Bart";
        List<VolontarioDto> lista = List.of(new VolontarioDto());
        when(service.findByNomeDto(nome)).thenReturn(lista);

        // WHEN
        List<VolontarioDto> result = controller.findByNome(nome);

        // THEN
        assertThat(result).hasSize(1);
        verify(service).findByNomeDto(nome);
    }

    // 🔹 TEST 2 - findByCf
    @Test
    void testFindByCf() {
        // GIVEN
        String cf = "ABC123";
        VolontarioDto dto = new VolontarioDto();
        when(service.findByCfDto(cf)).thenReturn(dto);

        // WHEN
        VolontarioDto result = controller.findByCf(cf);

        // THEN
        assertThat(result).isNotNull();
        verify(service).findByCfDto(cf);
    }

    // 🔹 TEST 3 - findByTurno
    @Test
    void testFindByTurno() {
        // GIVEN
        String turno = "Mattina";
        List<VolontarioDto> lista = List.of(new VolontarioDto(), new VolontarioDto());
        when(service.findByTurnoDto(turno)).thenReturn(lista);

        // WHEN
        List<VolontarioDto> result = controller.findByTurno(turno);

        // THEN
        assertThat(result).hasSize(2);
        verify(service).findByTurnoDto(turno);
    }
}