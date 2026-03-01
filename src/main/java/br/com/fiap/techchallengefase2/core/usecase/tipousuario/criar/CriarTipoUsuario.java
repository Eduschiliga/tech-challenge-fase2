package br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar;

public interface CriarTipoUsuario {
    Long criar(Long usuarioLogadoId, Long restauranteId, String nomeTipo);
}
