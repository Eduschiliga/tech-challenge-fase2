package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TipoUsuarioGateway {

    Long salvar(TipoUsuario tipoUsuario);

    Optional<TipoUsuario> buscarPorId(Long tipoUsuario);

    void deletarPorId(Long tipoUsuarioId);

    List<TipoUsuario> buscarTodosPorRestauranteId(Long restauranteId);
}
