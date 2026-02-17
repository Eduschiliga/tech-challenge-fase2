
package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RuleDadosUsuarioTest {

    private List<RuleDadosUsuario> ruleDadosUsuarioList;

    @BeforeEach
    void setUp() {
        ruleDadosUsuarioList = List.of(
                new ValidaSePossuiEmail(),
                new ValidaSePossuiLogin(),
                new ValidaSePossuiNome()
        );
    }

    @Nested
    @DisplayName("Validação de E-mail")
    class ValidacaoEmailTests {
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
                    "Endereço"
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
                    "Endereço"
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
                    "Endereço"
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
                    "Endereço"
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
                    "Endereço"
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
                    "Rua Principal, 123"
            );

            // Act & Assert
            assertThatCode(() -> {
                ruleDadosUsuarioList.forEach(rule -> rule.validar(cliente));
            }).doesNotThrowAnyException();
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
                    "Endereço"
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
                    "Rua Principal, 123"
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
                    "Rua Principal, 123"
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
                    "Endereço"
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
                    "Endereço"
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
                    "Endereço"
            );

            ValidaSePossuiEmail validaSePossuiEmail = new ValidaSePossuiEmail();

            // Act & Assert
            assertThatThrownBy(() -> validaSePossuiEmail.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Validação de Login")
    class ValidacaoLoginTests {
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

            ValidaSePossuiLogin validaSePossuiLogin = new ValidaSePossuiLogin();

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

            ValidaSePossuiLogin validaSePossuiLogin = new ValidaSePossuiLogin();

            // Act & Assert
            assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class);
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

            ValidaSePossuiLogin validaSePossuiLogin = new ValidaSePossuiLogin();

            // Act & Assert
            assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class);
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

            ValidaSePossuiLogin validaSePossuiLogin = new ValidaSePossuiLogin();

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

            ValidaSePossuiLogin validaSePossuiLogin = new ValidaSePossuiLogin();

            // Act & Assert
            // Dependendo da implementação, pode ser tratado como vazio
            assertThatThrownBy(() -> validaSePossuiLogin.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Validação de Nome")
    class ValidacaoNomeTests {

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

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

            ValidaSePossuiNome validaSePossuiNome = new ValidaSePossuiNome();

            // Act & Assert
            assertThatCode(() -> validaSePossuiNome.validar(cliente))
                    .doesNotThrowAnyException();
        }
    }
}