package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;

public interface BuscarTipoUsuarioPorId {

    TipoUsuario buscarPorId(Long usuarioLogadoId, Long tipoUsuarioId);

}
