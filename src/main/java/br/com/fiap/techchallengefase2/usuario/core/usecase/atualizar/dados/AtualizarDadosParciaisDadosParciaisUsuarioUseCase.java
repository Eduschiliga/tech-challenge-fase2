package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.BuscarUsuarioPorIdUseCase;

import java.util.List;

import static br.com.fiap.techchallengefase2.usuario.core.domain.factory.UsuarioFactory.atualizarDadosParciais;

/**
 * Atualiza apenas dados parciais do usuário como: Nome, e-mail, endereço e login.
 * */
public class AtualizarDadosParciaisDadosParciaisUsuarioUseCase implements AtualizarDadosParciaisUsuario {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final UsuarioGateway usuarioGateway;

    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;

    public AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            UsuarioGateway usuarioGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList,
            List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList
    ) {
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.usuarioGateway = usuarioGateway;
        this.ruleDadosUsuarioList = ruleDadosUsuarioList;
        this.ruleCredenciaisUsuarioList = ruleCredenciaisUsuarioList;
    }

    @Override
    public Long atualizar(Long usuarioLogadoId, DadosParciaisUsuarioDTO dadosParciaisDto) {
        UsuarioBase usuarioAtual = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        // Realiza a validação dos dados parciais
        ruleDadosUsuarioList.forEach(impl -> impl.validar(dadosParciaisDto));

        // Realiza a validação das credenciais
        ruleCredenciaisUsuarioList.forEach(impl -> impl.validar(usuarioAtual, dadosParciaisDto));

        UsuarioBase usuarioAtualizado = atualizarDadosParciais(usuarioAtual, dadosParciaisDto);

        return usuarioGateway.salvar(usuarioAtualizado);
    }
}
