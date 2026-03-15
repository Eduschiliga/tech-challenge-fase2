package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;

import java.util.List;

public interface BuscarTipoUsuarioPorUsuario {
    List<TipoUsuario> buscarPorUsuario(Long usuarioLogadoId, Long usuarioId);
}
