package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class UsuarioConfigTest {

    private final UsuarioConfig usuarioConfig = new UsuarioConfig();
    private final UsuarioGateway usuarioGateway = mock(UsuarioGateway.class);
    private final CodificadorSenhaGateway codificadorSenhaGateway = mock(CodificadorSenhaGateway.class);
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase = mock(BuscarTipoUsuarioPorIdUseCase.class);

    @Test
    void buscarUsuarioPorIdUseCase_DeveInstanciarCorretamente() {
        var bean = usuarioConfig.buscarUsuarioPorIdUseCase(usuarioGateway);
        assertNotNull(bean);
    }

    @Test
    void buscarTodosUsuarios_DeveInstanciarCorretamente() {
        BuscarTodosUsuarios bean = usuarioConfig.buscarTodosUsuarios(usuarioGateway);
        assertNotNull(bean);
    }

    @Test
    void criarUsuario_DeveInstanciarCorretamente() {
        List<RuleDadosUsuario> ruleDados = Collections.emptyList();
        List<RuleCredenciaisUsuario> ruleCredenciais = Collections.emptyList();

        CriarUsuario bean = usuarioConfig.criarUsuario(
                codificadorSenhaGateway,
                ruleDados,
                ruleCredenciais,
                usuarioGateway
        );
        assertNotNull(bean);
    }

    @Test
    void atualizarUsuario_DeveInstanciarCorretamente() {
        List<RuleDadosUsuario> ruleDados = Collections.emptyList();
        List<RuleCredenciaisUsuario> ruleCredenciais = Collections.emptyList();

        AtualizarUsuario bean = usuarioConfig.atualizarUsuario(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                ruleDados,
                ruleCredenciais
        );
        assertNotNull(bean);
    }

    @Test
    void atualizarSenhaUsuario_DeveInstanciarCorretamente() {
        List<RuleSenhaUsuario> ruleSenha = Collections.emptyList();

        AtualizarSenhaUsuario bean = usuarioConfig.atualizarSenhaUsuario(
                codificadorSenhaGateway,
                usuarioGateway,
                buscarUsuarioPorIdUseCase,
                ruleSenha
        );
        assertNotNull(bean);
    }

    @Test
    void deletarUsuario_DeveInstanciarCorretamente() {
        DeletarUsuario bean = usuarioConfig.deletarUsuario(usuarioGateway, buscarUsuarioPorIdUseCase);
        assertNotNull(bean);
    }

    @Test
    void atribuirTipoUsuario_DeveInstanciarCorretamente() {
        AtribuirTipoUsuario bean = usuarioConfig.atribuirTipoUsuario(
                buscarTipoUsuarioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                usuarioGateway
        );
        assertNotNull(bean);
    }

    @Test
    void removerTipoUsuario_DeveInstanciarCorretamente() {
        RemoverTipoUsuario bean = usuarioConfig.removerTipoUsuario(
                buscarTipoUsuarioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                usuarioGateway
        );
        assertNotNull(bean);
    }
}