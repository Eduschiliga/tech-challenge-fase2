package br.com.fiap.techchallengefase2.core.rule.credenciais;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.usuario.EmailJaCadastradoException;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteEmail;
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
class ValidaSeJaExisteEmailTest {
    @InjectMocks
    private ValidaSeJaExisteEmail validaSeJaExisteEmail;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    @DisplayName("Deve aceitar email que não existe no sistema")
    void deveAceitarEmailQueNaoExisteNoSistema() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao.novo@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        when(usuarioGateway.existeUsuarioComEmail("joao.novo@email.com"))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando email já existe no sistema")
    void deveFalharQuandoEmailJaExisteNoSistema() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "email.existente@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123"
        );

        when(usuarioGateway.existeUsuarioComEmail("email.existente@email.com"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> validaSeJaExisteEmail.validar(cliente))
                .isInstanceOf(EmailJaCadastradoException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usuario1@email.com",
            "admin@empresa.com.br",
            "test.user@domain.co.uk",
            "info_2024@company.org",
            "cliente.especial@loja.com"
    })
    @DisplayName("Deve aceitar diferentes emails únicos no sistema")
    void deveAceitarDiferentesEmailsUnicosNoSistema(String email) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste Usuario",
                email,
                "login.teste",
                "senha@123456",
                "Endereço Teste"
        );

        when(usuarioGateway.existeUsuarioComEmail(email))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteEmail.validar(cliente)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve validar email único para cliente")
    void deveValidarEmailUnicoParaCliente() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Cliente Teste",
                "cliente.unico@email.com",
                "cliente.login",
                "senha@123456",
                "Endereço Cliente"
        );

        when(usuarioGateway.existeUsuarioComEmail("cliente.unico@email.com"))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteEmail.validar(cliente)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve validar email único para dono")
    void deveValidarEmailUnicoParaDono() {
        // Arrange
        Dono dono = new Dono(
                null,
                "Dono Restaurante",
                "dono.unico@email.com",
                "dono.login",
                "senha@123456",
                "Endereço Dono",
                new ArrayList<>()
        );

        when(usuarioGateway.existeUsuarioComEmail("dono.unico@email.com"))
                .thenReturn(false);

        // Act & Assert
        assertThatCode(() -> validaSeJaExisteEmail.validar(dono)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar com email duplicado para diferentes tipos de usuário")
    void deveFalharComEmailDuplicadoParaDiferentesTiposDeUsuario() {
        // Arrange
        Dono dono = new Dono(
                null,
                "Dono Restaurante",
                "email.duplicado@email.com",
                "dono.login",
                "senha@123456",
                "Endereço Dono",
                new ArrayList<>()
        );

        when(usuarioGateway.existeUsuarioComEmail("email.duplicado@email.com")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> validaSeJaExisteEmail.validar(dono))
                .isInstanceOf(EmailJaCadastradoException.class);
    }
}