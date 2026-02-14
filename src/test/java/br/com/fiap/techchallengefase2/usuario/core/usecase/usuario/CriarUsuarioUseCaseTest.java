package br.com.fiap.techchallengefase2.usuario.core.usecase.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.usuario.RuleCriarUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes - CriarUsuarioUseCase")
class CriarUsuarioUseCaseTest {

    @InjectMocks
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @Mock
    private CodificadorSenhaGateway codificadorSenhaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private RuleCriarUsuario rule1;

    @Mock
    private RuleCriarUsuario rule2;

    @Mock
    private RuleCriarUsuario rule3;

    private static final String SENHA_ORIGINAL = "senha123";
    private static final String SENHA_CODIFICADA = "senhaCodificada123";
    private static final Long ID_ESPERADO = 1L;

    @Nested
    @DisplayName("Cenário de Sucesso")
    class CenarioSucesso {

        @Test
        @DisplayName("Deve criar usuário com sucesso - Caminho feliz")
        void deveCriarUsuarioComSucesso() {
            // Arrange
            Usuario usuario = new Usuario(null, "João Silva", "joao@email.com", "joao.silva", SENHA_ORIGINAL, "Rua A, 123");
            List<RuleCriarUsuario> rules = Arrays.asList(rule1, rule2, rule3);

            when(rule1.getOrdemValidacao()).thenReturn(1);
            when(rule2.getOrdemValidacao()).thenReturn(2);
            when(rule3.getOrdemValidacao()).thenReturn(3);

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(ID_ESPERADO);

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            Long idRetornado = criarUsuarioUseCase.criar(usuario);

            // Assert
            assertNotNull(idRetornado);
            assertEquals(ID_ESPERADO, idRetornado);
            assertEquals(SENHA_CODIFICADA, usuario.getSenha());
            assertNotEquals(SENHA_ORIGINAL, usuario.getSenha());

            // Verifica se todas as rules foram chamadas
            verify(rule1).validar(usuario);
            verify(rule2).validar(usuario);
            verify(rule3).validar(usuario);

            // Verifica se a senha foi codificada
            verify(codificadorSenhaGateway).codificar(SENHA_ORIGINAL);

            // Verifica se o usuário foi salvo
            verify(usuarioGateway).salvar(usuario);
        }

        @Test
        @DisplayName("Deve executar as rules em ordem de prioridade")
        void deveExecutarRulesEmOrdem() {
            // Arrange
            Usuario usuario = new Usuario(null, "Maria Silva", "maria@email.com", "maria.silva", SENHA_ORIGINAL, "Rua B, 456");

            when(rule1.getOrdemValidacao()).thenReturn(3);
            when(rule2.getOrdemValidacao()).thenReturn(1);
            when(rule3.getOrdemValidacao()).thenReturn(2);

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(ID_ESPERADO);

            List<RuleCriarUsuario> rules = Arrays.asList(rule1, rule2, rule3);
            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            criarUsuarioUseCase.criar(usuario);

            // Assert - Verifica a ordem de execução (rule2 primeiro, depois rule3, depois rule1)
            ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
            InOrder inOrder = inOrder(rule2, rule3, rule1);

            inOrder.verify(rule2).validar(captor.capture());
            inOrder.verify(rule3).validar(captor.capture());
            inOrder.verify(rule1).validar(captor.capture());
        }

        @Test
        @DisplayName("Deve codificar a senha corretamente")
        void deveCodificarSenhaCorretamente() {
            // Arrange
            Usuario usuario = new Usuario(null, "Pedro Santos", "pedro@email.com", "pedro.santos", SENHA_ORIGINAL, "Rua C, 789");
            List<RuleCriarUsuario> rules = new ArrayList<>();

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(ID_ESPERADO);

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            criarUsuarioUseCase.criar(usuario);

            // Assert
            assertEquals(SENHA_CODIFICADA, usuario.getSenha());
            verify(codificadorSenhaGateway, times(1)).codificar(SENHA_ORIGINAL);
        }

        @Test
        @DisplayName("Deve retornar o ID do usuário salvo")
        void deveRetornarIdDoUsuarioSalvo() {
            // Arrange
            Usuario usuario = new Usuario(null, "Ana Costa", "ana@email.com", "ana.costa", SENHA_ORIGINAL, "Rua D, 321");
            List<RuleCriarUsuario> rules = new ArrayList<>();
            Long idSalvo = 999L;

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(idSalvo);

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            Long idRetornado = criarUsuarioUseCase.criar(usuario);

            // Assert
            assertEquals(idSalvo, idRetornado);
            verify(usuarioGateway).salvar(usuario);
        }

    }

    @Nested
    @DisplayName("Cenário com Erro - Validação de Rules")
    class CenarioErroValidacao {

        @Test
        @DisplayName("Deve lançar exception quando a primeira rule falha")
        void deveLancarExceptionQuandoPrimeiraRuleFalha() {
            // Arrange
            Usuario usuario = new Usuario(null, "Carlos Mendes", "carlos@email.com", "carlos.mendes", SENHA_ORIGINAL, "Rua E, 654");

            doThrow(new IllegalArgumentException("Usuário já existe"))
                    .when(rule1).validar(usuario);

            when(rule1.getOrdemValidacao()).thenReturn(1);
            when(rule2.getOrdemValidacao()).thenReturn(2);

            List<RuleCriarUsuario> rules = Arrays.asList(rule1, rule2);
            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> criarUsuarioUseCase.criar(usuario));

            // Verifica que rule1 foi chamado
            verify(rule1).validar(usuario);

            // Verifica que rule2 NÃO foi chamado (pois rule1 falhou)
            verify(rule2, never()).validar(usuario);

            // Verifica que a senha NÃO foi codificada
            verify(codificadorSenhaGateway, never()).codificar(anyString());

            // Verifica que o usuário NÃO foi salvo
            verify(usuarioGateway, never()).salvar(usuario);
        }

        @Test
        @DisplayName("Deve lançar exception quando a segunda rule falha")
        void deveLancarExceptionQuandoSegundaRuleFalha() {
            // Arrange
            Usuario usuario = new Usuario(null, "Lucia Ferreira", "lucia@email.com", "lucia.ferreira", SENHA_ORIGINAL, "Rua F, 987");

            doThrow(new IllegalArgumentException("Email inválido"))
                    .when(rule2).validar(usuario);

            when(rule1.getOrdemValidacao()).thenReturn(1);
            when(rule2.getOrdemValidacao()).thenReturn(2);

            List<RuleCriarUsuario> rules = Arrays.asList(rule1, rule2);
            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> criarUsuarioUseCase.criar(usuario));

            // Verifica que ambas as rules foram chamadas na ordem correta
            verify(rule1).validar(usuario);
            verify(rule2).validar(usuario);

            // Verifica que a senha NÃO foi codificada
            verify(codificadorSenhaGateway, never()).codificar(anyString());

            // Verifica que o usuário NÃO foi salvo
            verify(usuarioGateway, never()).salvar(usuario);
        }

        @Test
        @DisplayName("Deve lançar exception quando a validação falha no gateway")
        void deveLancarExceptionQuandoGatewayFalha() {
            // Arrange
            Usuario usuario = new Usuario(null, "Roberto Lima", "roberto@email.com", "roberto.lima", SENHA_ORIGINAL, "Rua G, 147");
            List<RuleCriarUsuario> rules = new ArrayList<>();

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> criarUsuarioUseCase.criar(usuario));

            // Verifica que a senha foi codificada
            verify(codificadorSenhaGateway).codificar(SENHA_ORIGINAL);

            // Verifica que tentou salvar
            verify(usuarioGateway).salvar(usuario);
        }

    }

    @Nested
    @DisplayName("Cenário com Erro - Exceções em Gateways")
    class CenarioErroGateway {

        @Test
        @DisplayName("Deve lançar exception quando codificador falha")
        void deveLancarExceptionQuandoCodificadorFalha() {
            // Arrange
            Usuario usuario = new Usuario(null, "Fernanda Alves", "fernanda@email.com", "fernanda.alves", SENHA_ORIGINAL, "Rua H, 258");
            List<RuleCriarUsuario> rules = new ArrayList<>();

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL))
                    .thenThrow(new RuntimeException("Erro ao codificar senha"));

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> criarUsuarioUseCase.criar(usuario));

            // Verifica que o usuário NÃO foi salvo
            verify(usuarioGateway, never()).salvar(usuario);
        }

        @Test
        @DisplayName("Deve repassar exception do gateway de usuário")
        void deveRepassarExceptionDoGatewayDeUsuario() {
            // Arrange
            Usuario usuario = new Usuario(null, "Gabriel Costa", "gabriel@email.com", "gabriel.costa", SENHA_ORIGINAL, "Rua I, 369");
            List<RuleCriarUsuario> rules = new ArrayList<>();

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenThrow(new IllegalStateException("Banco de dados indisponível"));

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> criarUsuarioUseCase.criar(usuario));
        }

    }

    @Nested
    @DisplayName("Cenário com Lista de Rules Vazia")
    class CenarioRulesVazia {

        @Test
        @DisplayName("Deve criar usuário mesmo sem rules")
        void deveCriarUsuarioSemRules() {
            // Arrange
            Usuario usuario = new Usuario(null, "Isabela Martins", "isabela@email.com", "isabela.martins", SENHA_ORIGINAL, "Rua J, 741");
            List<RuleCriarUsuario> rules = new ArrayList<>();

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(ID_ESPERADO);

            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            Long idRetornado = criarUsuarioUseCase.criar(usuario);

            // Assert
            assertNotNull(idRetornado);
            assertEquals(ID_ESPERADO, idRetornado);
            verify(codificadorSenhaGateway).codificar(SENHA_ORIGINAL);
            verify(usuarioGateway).salvar(usuario);
        }

    }

    @Nested
    @DisplayName("Cenário com Múltiplas Rules")
    class CenarioMultiplasRules {

        @Test
        @DisplayName("Deve executar todas as rules em ordem")
        void deveExecutarTodasAsRulesEmOrdem() {
            // Arrange
            Usuario usuario = new Usuario(null, "Julieta Rocha", "julieta@email.com", "julieta.rocha", SENHA_ORIGINAL, "Rua K, 852");

            when(rule1.getOrdemValidacao()).thenReturn(10);
            when(rule2.getOrdemValidacao()).thenReturn(20);
            when(rule3.getOrdemValidacao()).thenReturn(30);

            when(codificadorSenhaGateway.codificar(SENHA_ORIGINAL)).thenReturn(SENHA_CODIFICADA);
            when(usuarioGateway.salvar(usuario)).thenReturn(ID_ESPERADO);

            List<RuleCriarUsuario> rules = Arrays.asList(rule1, rule2, rule3);
            criarUsuarioUseCase = new CriarUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, rules);

            // Act
            criarUsuarioUseCase.criar(usuario);

            // Assert
            verify(rule1).validar(usuario);
            verify(rule2).validar(usuario);
            verify(rule3).validar(usuario);
        }

    }

}