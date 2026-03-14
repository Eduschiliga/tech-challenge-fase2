package br.com.fiap.techchallengefase2.core.dto.tipousuario;

public record DesvincularUsuarioInputDTO(
        Long tipoUsuarioId,
        Long usuarioParaAtribuirId
) {
}
