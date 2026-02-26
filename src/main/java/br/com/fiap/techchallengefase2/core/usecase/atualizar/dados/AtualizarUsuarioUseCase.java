package br.com.fiap.techchallengefase2.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.credenciais.ValidaSeJaExisteEmail;
import br.com.fiap.techchallengefase2.core.rule.credenciais.ValidaSeJaExisteLogin;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.usecase.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory.atualizarDadosParciais;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCase implements AtualizarUsuario {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final UsuarioGateway usuarioGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;

    @Override
    public UsuarioBase atualizar(Long usuarioLogadoId, DadosUsuarioInputDTO dadosParciaisDto) {
        UsuarioBase usuarioAtual = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        UsuarioBase usuarioAtualizado = atualizarDadosParciais(usuarioAtual, dadosParciaisDto);

        validarCredenciais(usuarioAtual, usuarioAtualizado);
        validarDadosBasicos(usuarioAtualizado);

        return usuarioGateway.salvar(usuarioAtualizado);
    }

    private void validarCredenciais(UsuarioBase usuarioAtual, UsuarioBase usuarioAtualizado) {
        boolean houveAlteracaoEmail = !usuarioAtual.getEmail().equals(usuarioAtualizado.getEmail());
        boolean houveAlteracaoLogin = !usuarioAtual.getLogin().equals(usuarioAtualizado.getLogin());

        if (!houveAlteracaoEmail && !houveAlteracaoLogin) {
            return;
        }

        ruleCredenciaisUsuarioList.forEach(impl -> {
            if (impl instanceof ValidaSeJaExisteEmail && houveAlteracaoEmail) {
                impl.validar(usuarioAtualizado);
            } else if (impl instanceof ValidaSeJaExisteLogin && houveAlteracaoLogin) {
                impl.validar(usuarioAtualizado);
            }
        });
    }

    private void validarDadosBasicos(UsuarioBase usuarioAtualizado) {
        ruleDadosUsuarioList.forEach(impl -> impl.validar(usuarioAtualizado));
    }
}
