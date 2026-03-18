package br.com.fiap.techchallengefase2.core.rule.dono;

import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoDonoException;

public class ValidaSeUsuarioDono {

    public void validar(UsuarioBase usuarioBase) {
        if (!(usuarioBase instanceof Dono)) {
            throw new UsuarioNaoDonoException();
        }
    }

}
