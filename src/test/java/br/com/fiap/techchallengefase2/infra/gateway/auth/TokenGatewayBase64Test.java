package br.com.fiap.techchallengefase2.infra.gateway.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class TokenGatewayBase64Test {

    private TokenGatewayBase64 tokenGateway;

    @BeforeEach
    void setUp() {
        tokenGateway = new TokenGatewayBase64();
    }

    @Test
    void deveGerarTokenComSucesso() {
        Long usuarioId = 123L;
        String expectedToken = Base64.getEncoder().encodeToString(usuarioId.toString().getBytes());

        String tokenResult = tokenGateway.gerarToken(usuarioId);

        assertNotNull(tokenResult);
        assertEquals(expectedToken, tokenResult);
    }

    @Test
    void deveExtrairUsuarioIdComSucesso() {
        Long expectedId = 456L;
        String validToken = Base64.getEncoder().encodeToString(expectedId.toString().getBytes());

        Long idResult = tokenGateway.extrairUsuarioId(validToken);

        assertNotNull(idResult);
        assertEquals(expectedId, idResult);
    }

    @Test
    void deveRetornarNuloAoExtrairIdDeTokenComFormatoInvalido() {
        String invalidBase64Token = "@@@invalid_token!!!";

        Long idResult = tokenGateway.extrairUsuarioId(invalidBase64Token);

        assertNull(idResult);
    }

    @Test
    void deveRetornarNuloAoExtrairIdDeTokenComConteudoNaoNumerico() {
        String nonNumericToken = Base64.getEncoder().encodeToString("texto_ao_inves_de_id".getBytes());

        Long idResult = tokenGateway.extrairUsuarioId(nonNumericToken);

        assertNull(idResult);
    }
}