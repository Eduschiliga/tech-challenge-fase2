package br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.ValidaSenhaAtual;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
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
class AtualizarSenhaUsuarioUseCaseTest {

    @InjectMocks
    private AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;

    @Mock
    private CodificadorSenhaGateway codificadorSenhaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @BeforeEach
    void setUp() {
        List<RuleAtualizarSenhaUsuario> ruleCredenciaisUsuarioList = List.of(new ValidaSenhaAtual());

        atualizarSenhaUsuarioUseCase = new AtualizarSenhaUsuarioUseCase(
                codificadorSenhaGateway,
                usuarioGateway,
                buscarUsuarioPorIdUseCase,
                ruleCredenciaisUsuarioList
        );
    }

    @Test
    void deveAtualizarSenhaQuandoDadosForemValidos() {
        // Arrange
        Long usuarioLogadoId = 1L;
        AtualizarSenhaInputDTO atualizarSenhaInputDTO = new AtualizarSenhaInputDTO("novaSenha123", "senhaAtual123");

        // Criamos o usuário inicial com a senha antiga
        Dono usuario = new Dono(usuarioLogadoId, "Nome", "email@test.com", "login123", "senhaCodificadaVelha", "endereco", null);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuario);
        when(codificadorSenhaGateway.decodificar(usuario.getSenha())).thenReturn("senhaAtual123");
        when(codificadorSenhaGateway.codificar(atualizarSenhaInputDTO.getNovaSenha())).thenReturn("novaSenhaCodificada");

        // O Gateway agora devolve o objeto salvo
        when(usuarioGateway.salvar(usuario)).thenReturn(usuario);

        // Act
        UsuarioBase resultado = atualizarSenhaUsuarioUseCase.atualizar(usuarioLogadoId, atualizarSenhaInputDTO);

        // Assert
        assertNotNull(resultado);
        // Validamos se a Entidade recebeu corretamente a nova senha antes de salvar
        assertEquals("novaSenhaCodificada", resultado.getSenha());
        assertEquals(usuario, resultado);

        verify(codificadorSenhaGateway, times(1)).codificar(atualizarSenhaInputDTO.getNovaSenha());

        // Certificamos que o comportamento mudou: o Gateway salva a entidade inteira
        verify(usuarioGateway, times(1)).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualForIncorreta() {
        // Arrange
        Long usuarioLogadoId = 1L;
        AtualizarSenhaInputDTO atualizarSenhaInputDTO = new AtualizarSenhaInputDTO("senhaAtualErrada", "novaSenha123");
        Cliente usuario = new Cliente(usuarioLogadoId, "Nome", "email@test.com", "login123", "senhaCodificada", "endereco");

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuario);
        when(codificadorSenhaGateway.decodificar(usuario.getSenha())).thenReturn("senhaAtualCorreta");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> atualizarSenhaUsuarioUseCase.atualizar(usuarioLogadoId, atualizarSenhaInputDTO));

        assertEquals("Senha atual não confere", exception.getMessage());

        // Asseguramos que não chamou o salvar caso haja erro
        verify(usuarioGateway, never()).salvar(any(UsuarioBase.class));
    }
}