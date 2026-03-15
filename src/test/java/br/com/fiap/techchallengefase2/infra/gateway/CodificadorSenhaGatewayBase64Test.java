package br.com.fiap.techchallengefase2.infra.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CodificadorSenhaGatewayBase64Test {

    @InjectMocks
    private CodificadorSenhaGatewayBase64 codificadorSenhaGateway;

    @Test
    void deveCodificarSenhaComSucesso() {
        String senhaOriginal = "senha123";
        String senhaCodificadaEsperada = Base64.getEncoder().encodeToString(senhaOriginal.getBytes());

        String resultado = codificadorSenhaGateway.codificar(senhaOriginal);

        assertEquals(senhaCodificadaEsperada, resultado);
    }

    @Test
    void deveDecodificarSenhaComSucesso() {
        String senhaOriginal = "senha123";
        String senhaCodificada = Base64.getEncoder().encodeToString(senhaOriginal.getBytes());

        String resultado = codificadorSenhaGateway.decodificar(senhaCodificada);

        assertEquals(senhaOriginal, resultado);
    }
}