package br.com.fiap.techchallengefase2.core.rule.dono;

import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoDonoException;

public class ValidaSeUsuarioDonoRestaurante {

    public void validar(Dono dono, Long restauranteId) {
        if (!dono.isProprietario(restauranteId)) {
            throw new UsuarioNaoDonoException();
        }
    }

}
