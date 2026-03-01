package br.com.fiap.techchallengefase2.core.rule.dados.credenciais;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;

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
