package br.com.fiap.techchallengefase2.usuario.core.rule.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

public interface RuleCriarUsuario {

    void validar(Usuario usuario);

    default int getOrdemValidacao() {
        return 100;
    }
}
