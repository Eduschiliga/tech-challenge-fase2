
package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleCredenciaisUsuarioTest {

    private List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;

    @Mock
    private UsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        ruleCredenciaisUsuarioList = List.of(
                new ValidaSeJaExisteEmail(usuarioGateway),
                new ValidaSeJaExisteLogin(usuarioGateway)
        );
    }

    @Nested
    @DisplayName("Validação de Email Único")
    class ValidacaoEmailUnicoTests {

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

            ValidaSeJaExisteEmail validaSeJaExisteEmail = new ValidaSeJaExisteEmail(usuarioGateway);

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

            ValidaSeJaExisteEmail validaSeJaExisteEmail = new ValidaSeJaExisteEmail(usuarioGateway);

            // Act & Assert
            assertThatThrownBy(() -> validaSeJaExisteEmail.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Atualmente já existe um usuário cadastrado com o e-mail informado");
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

            ValidaSeJaExisteEmail validaSeJaExisteEmail = new ValidaSeJaExisteEmail(usuarioGateway);

            // Act & Assert
            assertThatCode(() -> validaSeJaExisteEmail.validar(cliente))
                    .doesNotThrowAnyException();
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
            assertThatCode(() -> {
                ruleCredenciaisUsuarioList.forEach(rule -> rule.validar(cliente));
            }).doesNotThrowAnyException();
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
            when(usuarioGateway.existeUsuarioComLogin("dono.login"))
                    .thenReturn(false);

            // Act & Assert
            assertThatCode(() -> {
                ruleCredenciaisUsuarioList.forEach(rule -> rule.validar(dono));
            }).doesNotThrowAnyException();
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

            when(usuarioGateway.existeUsuarioComEmail("email.duplicado@email.com"))
                    .thenReturn(true);

            ValidaSeJaExisteEmail validaSeJaExisteEmail = new ValidaSeJaExisteEmail(usuarioGateway);

            // Act & Assert
            assertThatThrownBy(() -> validaSeJaExisteEmail.validar(dono))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Validação de Login Único")
    class ValidacaoLoginUnicoTests {

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

            ValidaSeJaExisteLogin validaSeJaExisteLogin = new ValidaSeJaExisteLogin(usuarioGateway);

            // Act & Assert
            assertThatCode(() -> validaSeJaExisteLogin.validar(cliente))
                    .doesNotThrowAnyException();
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

            ValidaSeJaExisteLogin validaSeJaExisteLogin = new ValidaSeJaExisteLogin(usuarioGateway);

            // Act & Assert
            assertThatThrownBy(() -> validaSeJaExisteLogin.validar(cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Atualmente já existe um usuário cadastrado com o login informado");
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

            ValidaSeJaExisteLogin validaSeJaExisteLogin = new ValidaSeJaExisteLogin(usuarioGateway);

            // Act & Assert
            assertThatCode(() -> validaSeJaExisteLogin.validar(cliente))
                    .doesNotThrowAnyException();
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

            when(usuarioGateway.existeUsuarioComEmail("cliente@email.com"))
                    .thenReturn(false);
            when(usuarioGateway.existeUsuarioComLogin("cliente.login.unico"))
                    .thenReturn(false);

            // Act & Assert
            assertThatCode(() -> {
                ruleCredenciaisUsuarioList.forEach(rule -> rule.validar(cliente));
            }).doesNotThrowAnyException();
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

            when(usuarioGateway.existeUsuarioComEmail("dono@email.com"))
                    .thenReturn(false);
            when(usuarioGateway.existeUsuarioComLogin("dono.login.unico"))
                    .thenReturn(false);

            // Act & Assert
            assertThatCode(() -> {
                ruleCredenciaisUsuarioList.forEach(rule -> rule.validar(dono));
            }).doesNotThrowAnyException();
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

            ValidaSeJaExisteLogin validaSeJaExisteLogin = new ValidaSeJaExisteLogin(usuarioGateway);

            // Act & Assert
            assertThatThrownBy(() -> validaSeJaExisteLogin.validar(dono))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Atualmente já existe um usuário cadastrado com o login informado");
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

            ValidaSeJaExisteLogin validaSeJaExisteLogin = new ValidaSeJaExisteLogin(usuarioGateway);

            // Act & Assert
            assertThatCode(() -> validaSeJaExisteLogin.validar(cliente))
                    .doesNotThrowAnyException();
        }
    }
}