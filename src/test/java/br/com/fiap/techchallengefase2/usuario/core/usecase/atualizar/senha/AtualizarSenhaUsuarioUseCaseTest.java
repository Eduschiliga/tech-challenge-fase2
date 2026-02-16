package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.senha.AtualizarSenhaDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarSenhaUsuarioUseCaseTest {

    @Mock
    private CodificadorSenhaGateway codificadorSenhaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private RuleAtualizarSenhaUsuario ruleAtualizarSenhaUsuario;

    private AtualizarSenhaUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarSenhaUsuarioUseCase(
                codificadorSenhaGateway,
                usuarioGateway,
                buscarUsuarioPorIdUseCase,
                List.of(ruleAtualizarSenhaUsuario)
        );
    }

    private UsuarioBase criarUsuario(Long id, String senha) {
        return new Cliente(id, "Nome Teste", "teste@email.com", "login", senha, "Endereco Teste");
    }

    private AtualizarSenhaDTO criarAtualizarSenhaDTO(String senhaAtual, String novaSenha) {
        return new AtualizarSenhaDTO(senhaAtual, novaSenha);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesAtualizacaoSenhaComSucesso {

        @Test
        @DisplayName("Deve atualizar a senha do usuário com sucesso")
        void deveAtualizarSenhaComSucesso() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCase.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioId);
            verify(codificadorSenhaGateway).decodificar(senhaAtualCodificada);
            verify(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);
            verify(codificadorSenhaGateway).codificar(novaSenhaPlano);
            verify(usuarioGateway).salvar(usuario);
        }

        @Test
        @DisplayName("Deve codificar a nova senha antes de salvar")
        void deveCodeificarNovaSenhaAntesDeSalvar() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCase.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            ArgumentCaptor<UsuarioBase> usuarioCaptor = ArgumentCaptor.forClass(UsuarioBase.class);
            verify(usuarioGateway).salvar(usuarioCaptor.capture());

            UsuarioBase usuarioSalvo = usuarioCaptor.getValue();
            assertEquals(novaSenhaCodificada, usuarioSalvo.getSenha());
        }

        @Test
        @DisplayName("Deve decodificar a senha atual antes de validar")
        void deveDecodificarSenhaAtualAntesDaValidacao() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCase.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            verify(codificadorSenhaGateway).decodificar(senhaAtualCodificada);
            // Verifica que a validação ocorreu com a senha decodificada
            verify(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);
        }
    }

    @Nested
    @DisplayName("Testes de validação de senha atual")
    class TestesValidacaoSenhaAtual {

        @Test
        @DisplayName("Deve lançar exceção quando senha atual não confere")
        void deveLancarExcecaoQuandoSenhaAtualNaoConfere() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String senhaAtualIncorreta = "senhaIncorreta";
            String novaSenhaPlano = "novaSenha456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualIncorreta, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            doThrow(new IllegalArgumentException("Senha atual não confere"))
                    .when(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Senha atual não confere", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando nova senha é inválida")
        void deveLancarExcecaoQuandoNovaSenhaInvalida() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaInvalida = "";
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaInvalida);

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            doThrow(new IllegalArgumentException("Nova senha não atende aos critérios"))
                    .when(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Nova senha não atende aos critérios", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando nova senha é igual a atual")
        void deveLancarExcecaoQuandoNovaSenhaIgualAtual() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, senhaAtualPlano);

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            doThrow(new IllegalArgumentException("Nova senha não pode ser igual à atual"))
                    .when(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Nova senha não pode ser igual à atual", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Testes de busca de usuário")
    class TestesBuscaUsuario {

        @Test
        @DisplayName("Deve lançar exceção quando usuário não é encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            // Arrange
            Long usuarioId = 1L;
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO("senhaAtual", "novaSenha");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId))
                    .thenThrow(new RuntimeException("Usuário não encontrado"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Usuário não encontrado", exception.getMessage());
            verifyNoInteractions(codificadorSenhaGateway);
            verifyNoInteractions(usuarioGateway);
        }

        @Test
        @DisplayName("Deve lançar exceção quando ID do usuário é nulo")
        void deveLancarExcecaoQuandoUsuarioIdNulo() {
            // Arrange
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO("senhaAtual", "novaSenha");

            when(buscarUsuarioPorIdUseCase.buscarPorId(null))
                    .thenThrow(new IllegalArgumentException("ID do usuário é obrigatório"));

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(null, atualizarSenhaDTO)
            );

            assertEquals("ID do usuário é obrigatório", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes de codificação de senha")
    class TestesCodificacaoSenha {

        @Test
        @DisplayName("Deve lançar exceção quando falha ao codificar a nova senha")
        void deveLancarExcecaoQuandoFalhaAoCodificarNovaSenha() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano))
                    .thenThrow(new RuntimeException("Erro ao codificar a senha"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Erro ao codificar a senha", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando falha ao decodificar a senha atual")
        void deveLancarExcecaoQuandoFalhaAoDecodificarSenha() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO("senhaAtual", "novaSenha");

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada))
                    .thenThrow(new RuntimeException("Erro ao decodificar a senha"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Erro ao decodificar a senha", exception.getMessage());
            verifyNoInteractions(ruleAtualizarSenhaUsuario);
            verify(usuarioGateway, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Testes de erro no gateway")
    class TestesErroGateway {

        @Test
        @DisplayName("Deve lançar exceção quando falha ao salvar no gateway")
        void deveLancarExcecaoQuandoFalhaAoSalvar() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class)))
                    .thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, atualizarSenhaDTO)
            );

            assertEquals("Erro ao salvar no banco de dados", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes com múltiplas regras de validação")
    class TestesMultiplasRegras {

        @Test
        @DisplayName("Deve executar todas as regras de validação em ordem")
        void deveExecutarTodasAsRegrasEmOrdem() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            RuleAtualizarSenhaUsuario rule1 = mock(RuleAtualizarSenhaUsuario.class);
            RuleAtualizarSenhaUsuario rule2 = mock(RuleAtualizarSenhaUsuario.class);
            RuleAtualizarSenhaUsuario rule3 = mock(RuleAtualizarSenhaUsuario.class);

            when(rule1.getOrdemValidacao()).thenReturn(1);
            when(rule2.getOrdemValidacao()).thenReturn(2);
            when(rule3.getOrdemValidacao()).thenReturn(3);

            AtualizarSenhaUsuarioUseCase useCaseComMultiplasRegras =
                    new AtualizarSenhaUsuarioUseCase(
                            codificadorSenhaGateway,
                            usuarioGateway,
                            buscarUsuarioPorIdUseCase,
                            List.of(rule3, rule1, rule2) // Ordem propositalmente diferente
                    );

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCaseComMultiplasRegras.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            InOrder inOrder = inOrder(rule1, rule2, rule3);
            inOrder.verify(rule1).validar(senhaAtualPlano, atualizarSenhaDTO);
            inOrder.verify(rule2).validar(senhaAtualPlano, atualizarSenhaDTO);
            inOrder.verify(rule3).validar(senhaAtualPlano, atualizarSenhaDTO);
        }

        @Test
        @DisplayName("Deve parar na primeira falha de validação")
        void devePararNaPrimeiraFalha() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";

            RuleAtualizarSenhaUsuario rule1 = mock(RuleAtualizarSenhaUsuario.class);
            RuleAtualizarSenhaUsuario rule2 = mock(RuleAtualizarSenhaUsuario.class);

            when(rule1.getOrdemValidacao()).thenReturn(1);
            when(rule2.getOrdemValidacao()).thenReturn(2);

            doThrow(new IllegalArgumentException("Falha na primeira validação"))
                    .when(rule1).validar(eq(senhaAtualPlano), any(AtualizarSenhaDTO.class));

            AtualizarSenhaUsuarioUseCase useCaseComMultiplasRegras =
                    new AtualizarSenhaUsuarioUseCase(
                            codificadorSenhaGateway,
                            usuarioGateway,
                            buscarUsuarioPorIdUseCase,
                            List.of(rule1, rule2)
                    );

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);

            // Act & Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> useCaseComMultiplasRegras.atualizar(usuarioId, atualizarSenhaDTO)
            );

            verify(rule1).validar(senhaAtualPlano, atualizarSenhaDTO);
            verify(rule2, never()).validar(senhaAtualPlano, atualizarSenhaDTO);
        }
    }

    @Nested
    @DisplayName("Testes com listas vazias de regras")
    class TestesListasVazias {

        @Test
        @DisplayName("Deve funcionar sem regras de validação")
        void deveFuncionarSemRegras() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            AtualizarSenhaUsuarioUseCase useCaseSemRegras =
                    new AtualizarSenhaUsuarioUseCase(
                            codificadorSenhaGateway,
                            usuarioGateway,
                            buscarUsuarioPorIdUseCase,
                            Collections.emptyList()
                    );

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCaseSemRegras.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(usuarioGateway).salvar(usuario);
        }
    }

    @Nested
    @DisplayName("Testes de DTO")
    class TestesDTO {

        @Test
        @DisplayName("Deve validar quando DTO contém dados válidos")
        void deveValidarDTOComDadosValidos() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "senha_codificada_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "nova_senha_codificada_456";

            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCase.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert
            assertEquals(usuarioId, resultado);
            assertEquals(senhaAtualPlano, atualizarSenhaDTO.getSenhaAtual());
            assertEquals(novaSenhaPlano, atualizarSenhaDTO.getNovaSenha());
        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO é nulo")
        void deveLancarExcecaoQuandoDTONulo() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuario = criarUsuario(usuarioId, "senha_codificada");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);

            // Act & Assert
            assertThrows(
                    NullPointerException.class,
                    () -> useCase.atualizar(usuarioId, null)
            );

            verify(usuarioGateway, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Testes de integração de fluxo completo")
    class TestesFluxoCompleto {

        @Test
        @DisplayName("Deve completar o fluxo de atualização de senha com sucesso")
        void deveCompletarFluxoComSucesso() {
            // Arrange
            Long usuarioId = 1L;
            String senhaAtualCodificada = "hashed_password_123";
            String senhaAtualPlano = "senha123";
            String novaSenhaPlano = "novaSenha456";
            String novaSenhaCodificada = "hashed_new_password_456";

            UsuarioBase usuario = criarUsuario(usuarioId, senhaAtualCodificada);
            AtualizarSenhaDTO atualizarSenhaDTO = criarAtualizarSenhaDTO(senhaAtualPlano, novaSenhaPlano);

            // Configurar mocks
            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);
            when(codificadorSenhaGateway.decodificar(senhaAtualCodificada)).thenReturn(senhaAtualPlano);
            when(codificadorSenhaGateway.codificar(novaSenhaPlano)).thenReturn(novaSenhaCodificada);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCase.atualizar(usuarioId, atualizarSenhaDTO);

            // Assert - Verificar sequência de chamadas
            InOrder inOrder = inOrder(
                    buscarUsuarioPorIdUseCase,
                    codificadorSenhaGateway,
                    ruleAtualizarSenhaUsuario,
                    usuarioGateway
            );

            inOrder.verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioId);
            inOrder.verify(codificadorSenhaGateway).decodificar(senhaAtualCodificada);
            inOrder.verify(ruleAtualizarSenhaUsuario).validar(senhaAtualPlano, atualizarSenhaDTO);
            inOrder.verify(codificadorSenhaGateway).codificar(novaSenhaPlano);
            inOrder.verify(usuarioGateway).salvar(usuario);

            assertEquals(usuarioId, resultado);
        }
    }
}