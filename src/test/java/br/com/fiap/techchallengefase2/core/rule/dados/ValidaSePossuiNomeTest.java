package br.com.fiap.techchallengefase2.core.rule.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.rule.dados.ValidaSePossuiNome;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ValidaSePossuiNomeTest {

    @InjectMocks
    private ValidaSePossuiNome validaSePossuiNome;

    @Test
    @DisplayName("Deve passar na validação com nome válido")
    void devePassarComNomeValido() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiNome.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando nome é null")
    void devefalharQuandoNomeEhNull() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                null,
                "joao@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiNome.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve falhar quando nome está vazio")
    void devefalharQuandoNomeEstaVazio() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "",
                "joao@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiNome.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Maria",
            "João Silva",
            "José da Silva Santos",
            "Maria-Luiza",
            "José",
            "Ana Clara",
            "Pedro Henrique"
    })
    @DisplayName("Deve aceitar nomes com diferentes formatos válidos")
    void deveAceitarVariosFormatosDeNomesValidos(String nome) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                nome,
                "teste@email.com",
                "login",
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiNome.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando nome contém apenas espaços")
    void devefalharQuandoNomeApenasEspacos() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "   ",
                "teste@email.com",
                "login",
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiNome.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve aceitar nome muito longo")
    void deveAceitarNomeMuitoLongo() {
        // Arrange
        String nomeLongo = "A".repeat(500);
        Cliente cliente = new Cliente(
                null,
                nomeLongo,
                "teste@email.com",
                "login",
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiNome.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar nome com caracteres Unicode")
    void deveAceitarNomeComCaracteresUnicode() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "José María João 日本語",
                "teste@email.com",
                "login",
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiNome.validar(cliente))
                .doesNotThrowAnyException();
    }

}