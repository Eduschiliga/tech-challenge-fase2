package br.com.fiap.techchallengefase2.usuario.core.usecase.criar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;

import java.util.List;

public class CriarUsuarioUseCase implements CriarUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;
    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList, List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList
    ) {
        this.codificadorSenhaGateway = codificadorSenhaGateway;
        this.ruleDadosUsuarioList = ruleDadosUsuarioList;
        this.usuarioGateway = usuarioGateway;
        this.ruleCredenciaisUsuarioList = ruleCredenciaisUsuarioList;
    }

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
