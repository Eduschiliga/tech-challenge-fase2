package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VincularUsuarioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        VincularUsuarioJson vincularUsuarioJson = new VincularUsuarioJson(
                1L,
                2L
        );

        assertNotNull(vincularUsuarioJson);
        assertEquals(1L, vincularUsuarioJson.tipoUsuarioId());
        assertEquals(2L, vincularUsuarioJson.usuarioParaAtribuirId());
    }

    @Test
    void fromInput_DeveCriarInputDTOCorretamente() {
        VincularUsuarioInputDTO dto = VincularUsuarioJson.fromInput(1L, 2L);

        assertNotNull(dto);
        assertEquals(1L, dto.tipoUsuarioId());
        assertEquals(2L, dto.usuarioParaAtribuirId());
    }
}