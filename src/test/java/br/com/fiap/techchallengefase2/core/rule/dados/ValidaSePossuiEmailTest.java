package br.com.fiap.techchallengefase2.core.rule.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ValidaSePossuiEmailTest {

    @InjectMocks
    private ValidaSePossuiEmail validaSePossuiEmail;

    @Test
    @DisplayName("Deve aceitar email com ponto antes do @")
    void deveAceitarEmailComPontoAntesDoArroba() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario.sobrenome@email.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar email com números")
    void deveAceitarEmailComNumeros() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario123@email456.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar email com underscore")
    void deveAceitarEmailComUnderscore() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario_teste@emailempresa.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar email com hífen no domínio")
    void deveAceitarEmailComHifenNoDominio() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario@empresa-grande.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar email com extensão múltipla (.co.uk)")
    void deveAceitarEmailComExtensaoMultipla() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario@empresa.co.uk",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve passar na validação com email válido")
    void devePassarComEmailValido() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "joao.silva@email.com",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123",
                new ArrayList<>()
        );

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "user.name@example.co.uk",
            "test+tag@domain.com",
            "info_2024@company.org",
            "a@b.co",
            "usuario123@empresa.com.br"
    })
    @DisplayName("Deve aceitar diferentes formatos de email válidos")
    void deveAceitarVariosFormatosDeEmailValidos(String email) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                email,
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatCode(() -> validaSePossuiEmail.validar(cliente))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve falhar quando email é null")
    void devefalharQuandoEmailEhNull() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                null,
                "joao.login",
                "senha@123456",
                "Rua Principal, 123",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("E-mail não pode ser nulo ou vazio");
    }

    @Test
    @DisplayName("Deve falhar quando email está vazio")
    void devefalharQuandoEmailEstaVazio() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "João Silva",
                "",
                "joao.login",
                "senha@123456",
                "Rua Principal, 123",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("E-mail não pode ser nulo ou vazio");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "email.sem.arroba.com",
            "email@",
            "@example.com",
            "user@.com",
            "user@example",
            "user space@example.com",
            "user@exam ple.com",
            "usuario@@example.com",
            "usuario.example.com"
    })
    @DisplayName("Deve rejeitar emails com formato inválido")
    void deveRejeritarEmailsComFormatoInvalido(String emailInvalido) {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                emailInvalido,
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve falhar com email contendo espaços")
    void devefalharComEmailComEspacos() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario @example.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve falhar com email contendo caracteres especiais inválidos")
    void devefalharComCaracteresEspeciaisInvalidos() {
        // Arrange
        Cliente cliente = new Cliente(
                null,
                "Teste",
                "usuario#@example.com",
                "login",
                "senha@123456",
                "Endereço",
                new ArrayList<>()
        );

        ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

        // Act & Assert
        assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                .isInstanceOf(IllegalArgumentException.class);
    }
}