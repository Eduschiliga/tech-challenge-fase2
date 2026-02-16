package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.ValidaSePossuiSenha;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.BuscarUsuarioPorIdUseCase;

import java.util.List;
import java.util.Objects;

/**
 * Atualiza apenas dados parciais do usuário como: Nome, e-mail, endereço e login.
 * */
public class AtualizarDadosParciaisDadosParciaisUsuarioUseCase implements AtualizarDadosParciaisUsuario {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final UsuarioGateway usuarioGateway;

    private List<RuleDadosUsuario> ruleDadosUsuarioList;
    private List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;

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
    public Long atualizar(Long usuarioLogadoId, UsuarioBase usuarioAtualizar) {
        validarSeMesmoUsuario(usuarioLogadoId, usuarioAtualizar);

        UsuarioBase usuarioAtual = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        validarDados(usuarioAtualizar);
        validarCredenciais(usuarioAtualizar, usuarioAtual);

        usuarioAtual.atualizarDadosParciais(usuarioAtualizar);

        return usuarioGateway.salvar(usuarioAtual);
    }

    private void validarCredenciais(UsuarioBase usuarioAtualizar, UsuarioBase usuarioAtual) {
        ruleCredenciaisUsuarioList.forEach(impl -> impl.validar(usuarioAtual, usuarioAtualizar));
    }

    private void validarDados(UsuarioBase usuario) {
        ruleDadosUsuarioList.
                stream().filter((impl) -> !(impl instanceof ValidaSePossuiSenha))
                .forEach(impl -> impl.validar(usuario));
    }

    private void validarSeMesmoUsuario(Long usuarioLogadoId, UsuarioBase usuarioAtualizar) {
        if (!Objects.equals(usuarioLogadoId, usuarioAtualizar.getUsuarioId())) {
            throw new IllegalArgumentException("Não é possível realizar a alteração do registro de outros usuários");
        }
    }

}
