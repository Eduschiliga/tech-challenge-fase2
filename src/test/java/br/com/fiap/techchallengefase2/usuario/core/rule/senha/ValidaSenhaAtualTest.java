package br.com.fiap.techchallengefase2.usuario.core.rule.senha;

import br.com.fiap.techchallengefase2.core.dto.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.rule.senha.ValidaSenhaAtual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaSenhaAtualTest {

    @InjectMocks
    private ValidaSenhaAtual validaSenhaAtual;

    @Test
    void deveLancarExcecaoQuandoSenhaAtualNaoConfere() {
        // Arrange
        String senhaAtual = "senhaCorreta123";
        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO("any-senhahahah", "senhaIncorreta456");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validaSenhaAtual.validar(senhaAtual, dto));

        assertEquals("Senha atual não confere", exception.getMessage());
    }

    @Test
    void naoDeveLancarExcecaoQuandoSenhaAtualConfere() {
        // Arrange
        String senhaAtual = "senhaCorreta123";
        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO("any-senhahahah", senhaAtual);

        // Act & Assert
        assertDoesNotThrow(() -> validaSenhaAtual.validar(senhaAtual, dto));
    }

    @Test
    void deveRetornarOrdemValidacaoIgualAUm() {
        // Act
        int ordemValidacao = validaSenhaAtual.getOrdemValidacao();

        // Assert
        assertEquals(1, ordemValidacao);
    }
}