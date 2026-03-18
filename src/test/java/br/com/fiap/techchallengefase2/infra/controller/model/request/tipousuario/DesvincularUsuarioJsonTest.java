package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DesvincularUsuarioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        DesvincularUsuarioJson desvincularUsuarioJson = new DesvincularUsuarioJson(
                5L,
                15L
        );

        assertNotNull(desvincularUsuarioJson);
        assertEquals(5L, desvincularUsuarioJson.tipoUsuarioId());
        assertEquals(15L, desvincularUsuarioJson.usuarioParaAtribuirId());
    }

    @Test
    void fromInput_DeveCriarInputDTOCorretamente() {
        DesvincularUsuarioInputDTO dto = DesvincularUsuarioJson.fromInput(5L, 15L);

        assertNotNull(dto);
        assertEquals(5L, dto.tipoUsuarioId());
        assertEquals(15L, dto.usuarioParaAtribuirId());
    }
}