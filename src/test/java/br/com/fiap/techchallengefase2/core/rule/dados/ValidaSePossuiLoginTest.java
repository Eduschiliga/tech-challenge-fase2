package br.com.fiap.techchallengefase2.core.rule.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.exception.DadosUsuarioInvalidosException;
import br.com.fiap.techchallengefase2.core.rule.dados.ValidaSePossuiLogin;
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
class ValidaSePossuiLoginTest {

    @InjectMocks
    private ValidaSePossuiLogin validaSePossuiLogin;

    @Test
    @DisplayName("Deve passar na validação com login válido")
    void devePassarComLoginValido() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                "joao.silva.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiLogin.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando login é null")
    void devefalharQuandoLoginEhNull() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                null,
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                .isInstanceOf(DadosUsuarioInvalidosException.class);
    }

    @Test
    @DisplayName("Deve falhar quando login está vazio")
    void devefalharQuandoLoginEstaVazio() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                "",
                "senha@123456",
                "Rua Principal, 123"
        );

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                .isInstanceOf(DadosUsuarioInvalidosException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usuario123",
            "admin_user",
            "user.name",
            "LOGIN_MAIUSCULA",
            "user-name",
            "a",
            "user1234567890"
    })
    @DisplayName("Deve aceitar logins com diferentes formatos válidos")
    void deveAceitarVariosFormatosDeLoginValidos(String login) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "teste@email.com",
                login,
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiLogin.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando login contém apenas espaços")
    void devefalharQuandoLoginApenasEspacos() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "teste@email.com",
                "   ",
                "senha@123456",
                "Endereço"
        );

        // Act & Assert
        // Dependendo da implementação, pode ser tratado como vazio
        assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                .isInstanceOf(DadosUsuarioInvalidosException.class);
    }
}