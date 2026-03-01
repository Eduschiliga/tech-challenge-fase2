package br.com.fiap.techchallengefase2.core.usecase.usuario.criar;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CriarUsuarioUseCase implements CriarUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;
    private final UsuarioGateway usuarioGateway;

    @Override
    public UsuarioBase criar(UsuarioBase usuario) {
        validarDadosBasicos(usuario);
        validarCredenciais(usuario);

        definirSenhaCodificada(usuario);

        return usuarioGateway.salvar(usuario);
    }

    private void definirSenhaCodificada(UsuarioBase usuario) {
        String senhaCodificada = codificadorSenhaGateway.codificar(usuario.getSenha());

        usuario.atribuirSenhaCodificada(senhaCodificada);
    }

    private void validarCredenciais(UsuarioBase usuario) {
        ruleCredenciaisUsuarioList.forEach(impl -> impl.validar(usuario));
    }

    private void validarDadosBasicos(UsuarioBase usuario) {
        ruleDadosUsuarioList.forEach(rule -> rule.validar(usuario));
    }
}
