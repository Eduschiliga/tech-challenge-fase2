package br.com.fiap.techchallengefase2.infra.config;

import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuariosUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsuarioConfig {

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarUsuarioPorIdUseCase(usuarioGateway);
    }

    @Bean
    public BuscarTodosUsuarios buscarTodosUsuarios(UsuarioGateway usuarioGateway) {
        return new BuscarTodosUsuariosUseCase(usuarioGateway);
    }

    @Bean
    public CriarUsuario criarUsuario(
            CodificadorSenhaGateway codificadorSenhaGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList,
            List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList,
            UsuarioGateway usuarioGateway
    ) {
        return new CriarUsuarioUseCase(codificadorSenhaGateway, ruleDadosUsuarioList, ruleCredenciaisUsuarioList, usuarioGateway);
    }

    @Bean
    public AtualizarUsuario atualizarUsuario(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            UsuarioGateway usuarioGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList,
            List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList
    ) {
        return new AtualizarUsuarioUseCase(buscarUsuarioPorIdUseCase, usuarioGateway, ruleDadosUsuarioList, ruleCredenciaisUsuarioList);
    }

    @Bean
    public AtualizarSenhaUsuario atualizarSenhaUsuario(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            List<RuleAtualizarSenhaUsuario> ruleAtualizarSenhaUsuarioList
    ) {
        return new AtualizarSenhaUsuarioUseCase(codificadorSenhaGateway, usuarioGateway, buscarUsuarioPorIdUseCase, ruleAtualizarSenhaUsuarioList);
    }

    @Bean
    public DeletarUsuario deletarUsuario(
            UsuarioGateway usuarioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase
    ) {
        return new DeletarUsuarioUseCase(usuarioGateway, buscarUsuarioPorIdUseCase);
    }

    @Bean
    public AtribuirTipoUsuario atribuirTipoUsuario(
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            UsuarioGateway usuarioGateway
    ) {
        return new AtribuirTipoUsuarioUseCase(buscarTipoUsuarioPorIdUseCase, buscarUsuarioPorIdUseCase, usuarioGateway);
    }

    @Bean
    public RemoverTipoUsuario removerTipoUsuario(
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            UsuarioGateway usuarioGateway
    ) {
        return new RemoverTipoUsuarioUseCase(buscarTipoUsuarioPorIdUseCase, buscarUsuarioPorIdUseCase, usuarioGateway);
    }
}