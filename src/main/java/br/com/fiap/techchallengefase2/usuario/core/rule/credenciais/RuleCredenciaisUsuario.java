package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface RuleCredenciaisUsuario {

    void validar(UsuarioBase usuarioAtual, UsuarioBase usuarioParaAtualizar);

}
