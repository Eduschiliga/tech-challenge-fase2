package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.credenciais.ValidaSeJaExisteEmail;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.usecase.atualizar.dados.AtualizarUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioUseCaseTest {

    @InjectMocks
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList = List.of(new ValidaSeJaExisteEmail(usuarioGateway));
        List<RuleDadosUsuario> ruleDadosUsuarioList = List.of(mock(RuleDadosUsuario.class));

        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                ruleDadosUsuarioList,
                ruleCredenciaisUsuarioList
        );
    }

    @Test
    void deveAtualizarUsuarioQuandoDadosForemValidos() {
        // Arrange
        Long usuarioLogadoId = 1L;
        DadosUsuarioInputDTO dadosUsuarioInputDTO = new DadosUsuarioInputDTO("Novo Nome", "novoemail@test.com", "novologin", "novoEndereco");
        Dono usuarioAtual = new Dono(usuarioLogadoId, "Antigo Nome", "antigoemail@test.com", "antigologin", "senhaCodificada", "antigoEndereco", null);
        Dono usuarioAtualizado = new Dono(usuarioLogadoId, "Novo Nome", "novoemail@test.com", "novologin", "senhaCodificada", "novoEndereco", null);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioAtual);
        when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioAtualizado);

        // Act
        UsuarioBase resultado = atualizarUsuarioUseCase.atualizar(usuarioLogadoId, dadosUsuarioInputDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("novoemail@test.com", resultado.getEmail());
        verify(usuarioGateway, times(1)).salvar(any(UsuarioBase.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExistir() {
        // Arrange
        Long usuarioLogadoId = 1L;
        DadosUsuarioInputDTO dadosUsuarioInputDTO = new DadosUsuarioInputDTO("Novo Nome", "emailjaexiste@test.com", "novologin", "novoEndereco");
        Dono usuarioAtual = new Dono(usuarioLogadoId, "Antigo Nome", "antigoemail@test.com", "antigologin", "senhaCodificada", "antigoEndereco", null);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioAtual);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> atualizarUsuarioUseCase.atualizar(usuarioLogadoId, dadosUsuarioInputDTO));

        verify(usuarioGateway, never()).salvar(any(UsuarioBase.class));
    }
}