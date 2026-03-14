package br.com.fiap.techchallengefase2.core.dto.tipousuario;

public record VincularUsuarioInputDTO(
        Long tipoUsuarioId,
        Long usuarioParaAtribuirId
) {
}
