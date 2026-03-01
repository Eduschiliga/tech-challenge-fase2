package br.com.fiap.techchallengefase2.core.rule.credenciais;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaSeJaExisteLoginTest {
    @InjectMocks
    private ValidaSeJaExisteLogin validaSeJaExisteLogin;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    @DisplayName("Deve aceitar login que não existe no sistema")
    void deveAceitarLoginQueNaoExisteNoSistema() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                "login.novo.unico",
                "senha@123456",
                "Rua Principal, 123"
        );

        when(usuarioGateway.existeUsuarioComLogin("login.novo.unico"))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteLogin.validar(cliente)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando login já existe no sistema")
    void deveFalharQuandoLoginJaExisteNoSistema() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao@email.com",
                "login.existente",
                "senha@123456",
                "Rua Principal, 123"
        );

        when(usuarioGateway.existeUsuarioComLogin("login.existente"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> validaSeJaExisteLogin.validar(cliente)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "admin_2024",
            "usuario.teste",
            "cliente_especial",
            "dono-restaurante",
            "login123",
            "user_unique"
    })
    @DisplayName("Deve aceitar diferentes logins únicos no sistema")
    void deveAceitarDiferentesLoginsUnicosNoSistema(String login) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste Usuario",
                "teste@email.com",
                login,
                "senha@123456",
                "Endereço Teste"
        );

        when(usuarioGateway.existeUsuarioComLogin(login))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteLogin.validar(cliente)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve validar login único para cliente")
    void deveValidarLoginUnicoParaCliente() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Cliente Teste",
                "cliente@email.com",
                "cliente.login.unico",
                "senha@123456",
                "Endereço Cliente"
        );

        when(usuarioGateway.existeUsuarioComLogin("cliente.login.unico")).thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteLogin.validar(cliente)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve validar login único para dono")
    void deveValidarLoginUnicoParaDono() {
        // Arrange
        Dono dono = new Dono(
                null,
                "Dono Restaurante",
                "dono@email.com",
                "dono.login.unico",
                "senha@123456",
                "Endereço Dono",
                new ArrayList<>()
        );

        when(usuarioGateway.existeUsuarioComLogin("dono.login.unico")).thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteLogin.validar(dono)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar com login duplicado para diferentes tipos de usuário")
    void deveFalharComLoginDuplicadoParaDiferentesTiposDeUsuario() {
        // Arrange
        Dono dono = new Dono(
                null,
                "Dono Restaurante",
                "dono@email.com",
                "login.duplicado",
                "senha@123456",
                "Endereço Dono",
                new ArrayList<>()
        );

        when(usuarioGateway.existeUsuarioComLogin("login.duplicado"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> validaSeJaExisteLogin.validar(dono))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve aceitar login com caracteres especiais permitidos quando único")
    void deveAceitarLoginComCaracteresEspeciaisQuandoUnico() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "teste@email.com",
                "user_test-2024.login",
                "senha@123456",
                "Endereço"
        );

        when(usuarioGateway.existeUsuarioComLogin("user_test-2024.login"))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteLogin.validar(cliente))
                .doesNotThrowAnyException();
    }
}