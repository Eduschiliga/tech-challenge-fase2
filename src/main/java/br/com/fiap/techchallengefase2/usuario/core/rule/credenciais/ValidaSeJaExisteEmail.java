package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

public class ValidaSeJaExisteEmail implements RuleCredenciaisUsuario {
    private final UsuarioGateway usuarioGateway;

    public ValidaSeJaExisteEmail(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void validar(UsuarioBase usuario) {
        boolean existeUsuarioComEmail = usuarioGateway.existeUsuarioComEmail(usuario.getEmail());

        if (existeUsuarioComEmail) {
            throw new IllegalArgumentException("Atualmente já existe um usuário cadastrado com o e-mail informado");
        }
    }
}
