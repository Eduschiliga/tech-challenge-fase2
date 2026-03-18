package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AtualizarSenhaJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        AtualizarSenhaJson atualizarSenhaJson = new AtualizarSenhaJson(
                "novaSenha123",
                "senhaAntiga123"
        );

        assertNotNull(atualizarSenhaJson);
        assertEquals("novaSenha123", atualizarSenhaJson.novaSenha());
        assertEquals("senhaAntiga123", atualizarSenhaJson.senhaAtual());
    }

    @Test
    void fromInput_DeveCriarInputDTOCorretamente() {
        AtualizarSenhaInputDTO dto = AtualizarSenhaJson.fromInput("novaSenha123", "senhaAntiga123");

        assertNotNull(dto);
        assertEquals("novaSenha123", dto.getNovaSenha());
        assertEquals("senhaAntiga123", dto.getSenhaAtual());
    }
}