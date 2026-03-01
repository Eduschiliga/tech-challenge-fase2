package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir;

public interface AtribuirTipoUsuario {

    void atribuirTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioParaAtribuirId);

}
