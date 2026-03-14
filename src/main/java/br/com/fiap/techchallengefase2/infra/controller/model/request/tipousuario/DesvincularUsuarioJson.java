package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;

public record DesvincularUsuarioJson(
        Long tipoUsuarioId,
        Long usuarioParaAtribuirId
) {

    public static DesvincularUsuarioInputDTO fromInput(Long tipoUsuarioId, Long usuarioParaAtribuirId) {
        return new DesvincularUsuarioInputDTO(tipoUsuarioId, usuarioParaAtribuirId);
    }

}
