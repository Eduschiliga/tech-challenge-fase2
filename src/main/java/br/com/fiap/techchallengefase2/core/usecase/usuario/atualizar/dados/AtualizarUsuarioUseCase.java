package br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteEmail;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteLogin;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCase implements AtualizarUsuario {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final UsuarioGateway usuarioGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;

    @Override
    public UsuarioBase atualizar(Long usuarioLogadoId, DadosUsuarioInputDTO dadosParciaisDto) {
        UsuarioBase usuarioAtual = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        UsuarioBase usuarioAtualizado = usuarioAtual.atualizar(
                dadosParciaisDto.getNome(),
                dadosParciaisDto.getEmail(),
                dadosParciaisDto.getLogin(),
                dadosParciaisDto.getEndereco()
        );

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
