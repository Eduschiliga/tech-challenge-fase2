package br.com.fiap.techchallengefase2.core.rule.dados.credenciais;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.LoginJaCadastradoException;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;

public class ValidaSeJaExisteLogin implements RuleCredenciaisUsuario {
    private final UsuarioGateway usuarioGateway;

    public ValidaSeJaExisteLogin(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void validar(UsuarioBase usuario) {
        boolean existeUsuarioComLogin = usuarioGateway.existeUsuarioComLogin(usuario.getLogin());

        if (existeUsuarioComLogin) {
            throw new LoginJaCadastradoException();
        }
    }
}
