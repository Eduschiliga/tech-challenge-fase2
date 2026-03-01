package br.com.fiap.techchallengefase2.usuario.core.usecase.usuario.criar;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.usecase.ususario.criar.CriarUsuarioUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CriarUsuarioUseCaseTest {
    public static final String HASHED_PASSWORD = "senha-codificada";

    @InjectMocks
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @Mock
    private CodificadorSenhaGateway codificadorSenhaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    private Cliente clienteValido;
    private Dono donoValido;
    private Cliente clienteSalvo;
    private Dono donoSalvo;

    @BeforeEach
    void setUp() {
        donoValido = new Dono(
                null,
                "Maria Santos",
                "maria.santos@email.com",
                "maria.login",
                "any-senha",
                "Av. Secundária, 456",
                new ArrayList<>()
        );

        donoSalvo = new Dono(
                2L,
                "Maria Santos",
                "maria.santos@email.com",
                "maria.login",
                HASHED_PASSWORD,
                "Av. Secundária, 456",
                new ArrayList<>()
        );

        clienteSalvo = new Cliente(
                1L,
                "João Silva",
                "joao.silva@email.com",
                "joao.login",
                HASHED_PASSWORD,
                "Rua Principal, 123"
        );

        clienteValido = new Cliente(
                null,
                "João Silva",
                "joao.silva@email.com",
                "joao.login",
                "any-senha",
                "Rua Principal, 123"
        );

        List<RuleDadosUsuario> ruleDadosUsuarioList = List.of(Mockito.mock(RuleDadosUsuario.class));

        List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList = List.of(Mockito.mock(RuleCredenciaisUsuario.class));

        criarUsuarioUseCase = new CriarUsuarioUseCase(
                codificadorSenhaGateway,
                ruleDadosUsuarioList,
                ruleCredenciaisUsuarioList,
                usuarioGateway
        );
    }

    @Nested
    @DisplayName("Testes de criação de usuário com sucesso")
    class SucessoTests {
        @Test
        @DisplayName("Deve criar dono com sucesso")
        void deveCriarDonoComSucesso() {
            String senha = donoValido.getSenha();

            when(codificadorSenhaGateway.codificar(donoValido.getSenha())).thenReturn(HASHED_PASSWORD);
            when(usuarioGateway.salvar(donoValido)).thenReturn(donoSalvo);

            UsuarioBase usuarioCriado = criarUsuarioUseCase.criar(donoValido);

            assertThat(usuarioCriado).isInstanceOf(Dono.class);
            assertNotEquals(senha, usuarioCriado.getSenha());
            assertEquals(HASHED_PASSWORD, usuarioCriado.getSenha());
            assertEquals(donoValido.getNome(), usuarioCriado.getNome());
            assertEquals(donoValido.getEmail(), usuarioCriado.getEmail());
            assertEquals(donoValido.getLogin(), usuarioCriado.getLogin());
            assertEquals(donoValido.getEndereco(), usuarioCriado.getEndereco());
        }

        @Test
        @DisplayName("Deve salvar o usuário com a senha codificada")
        void deveSalvarUsuarioComSenhaCodificada() {
            String senha = clienteValido.getSenha();

            when(codificadorSenhaGateway.codificar(clienteValido.getSenha())).thenReturn(HASHED_PASSWORD);
            when(usuarioGateway.salvar(any())).thenReturn(clienteSalvo);

            UsuarioBase usuarioSalvo = criarUsuarioUseCase.criar(clienteValido);

            assertThat(usuarioSalvo.getSenha()).isEqualTo(HASHED_PASSWORD).isNotEqualTo(senha);
        }

        @Test
        @DisplayName("Deve criar cliente com sucesso")
        void deveCriarClienteComSucesso() {
            String senha = clienteValido.getSenha();

            when(codificadorSenhaGateway.codificar(clienteValido.getSenha())).thenReturn(HASHED_PASSWORD);
            when(usuarioGateway.salvar(clienteValido)).thenReturn(clienteSalvo);

            UsuarioBase usuarioCriado = criarUsuarioUseCase.criar(clienteValido);

            assertThat(usuarioCriado).isInstanceOf(Cliente.class);
            assertNotEquals(senha, usuarioCriado.getSenha());
            assertEquals(HASHED_PASSWORD, usuarioCriado.getSenha());
            assertEquals(clienteValido.getNome(), usuarioCriado.getNome());
            assertEquals(clienteValido.getEmail(), usuarioCriado.getEmail());
            assertEquals(clienteValido.getLogin(), usuarioCriado.getLogin());
            assertEquals(clienteValido.getEndereco(), usuarioCriado.getEndereco());
        }
    }
}