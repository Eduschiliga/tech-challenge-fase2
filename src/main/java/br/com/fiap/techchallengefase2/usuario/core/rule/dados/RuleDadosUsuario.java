package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface RuleDadosUsuario {

    void validar(UsuarioBase usuario);

    default int getOrdemValidacao() {
        return 100;
    }
}
