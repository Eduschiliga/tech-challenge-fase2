package br.com.fiap.techchallengefase2.core.rule.credenciais;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

public interface RuleCredenciaisUsuario {

    void validar(UsuarioBase usuarioAtual);
}
