package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover;

public interface RemoverTipoUsuario {
    void removerTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioAlvoId);
}
