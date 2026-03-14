package br.com.fiap.techchallengefase2.core.rule.senha;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.exception.NovaSenhaInvalidaException;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.ValidaSenha;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaSenhaTest {

    @InjectMocks
    private ValidaSenha validaSenha;

    @Test
    void deveLancarExcecaoQuandoNovaSenhaForNula() {
        // Arrange
        String senhaAtual = "senhaAntiga123";
        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO(null, senhaAtual);

        // Act & Assert
        NovaSenhaInvalidaException exception = assertThrows(NovaSenhaInvalidaException.class,
                () -> validaSenha.validar(senhaAtual, dto));

        assertEquals("Nova senha não pode ser vazia ou menor que 8 caracteres", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNovaSenhaForVazia() {
        // Arrange
        String senhaAtual = "senhaAntiga123";
        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO("", senhaAtual);

        // Act & Assert
        NovaSenhaInvalidaException exception = assertThrows(NovaSenhaInvalidaException.class,
                () -> validaSenha.validar(senhaAtual, dto));

        assertEquals("Nova senha não pode ser vazia ou menor que 8 caracteres", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNovaSenhaForMenorQue8Caracteres() {
        // Arrange
        String senhaAtual = "senhaAntiga123";
        String novaSenha = "1234567";
        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO(novaSenha, senhaAtual);

        // Act & Assert
        NovaSenhaInvalidaException exception = assertThrows(NovaSenhaInvalidaException.class,
                () -> validaSenha.validar(senhaAtual, dto));

        assertEquals("Nova senha não pode ser vazia ou menor que 8 caracteres", exception.getMessage());
    }

    @Test
    void naoDeveLancarExcecaoParaNovaSenhaValida() {
        // Arrange
        String senhaAtual = "senhaAntiga123";
        String novaSenha = "NovaSenha123";

        AtualizarSenhaInputDTO dto = new AtualizarSenhaInputDTO(novaSenha, senhaAtual);

        // Act & Assert
        assertDoesNotThrow(() -> validaSenha.validar(senhaAtual, dto));
    }

    @Test
    void deveRetornarOrdemValidacaoIgualAUm() {
        // Act
        int ordemValidacao = validaSenha.getOrdemValidacao();

        // Assert
        assertEquals(2, ordemValidacao);
    }
}