package br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar;

public interface AtualizarTipoUsuario {

    Long atualizar(Long usuarioLogadoId, Long tipoUsuarioId, String nomeTipo);

}
