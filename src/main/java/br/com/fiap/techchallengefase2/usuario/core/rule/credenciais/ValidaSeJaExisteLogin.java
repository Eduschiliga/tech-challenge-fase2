package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

public class ValidaSeJaExisteLogin implements RuleCredenciaisUsuario {
    private final UsuarioGateway usuarioGateway;

    public ValidaSeJaExisteLogin(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void validar(UsuarioBase usuario) {
        boolean existeUsuarioComLogin = usuarioGateway.existeUsuarioComLogin(usuario.getLogin());

            if (existeUsuarioComLogin) {
                throw new IllegalArgumentException("Atualmente já existe um usuário cadastrado com o login informado");
            }
    }
}
