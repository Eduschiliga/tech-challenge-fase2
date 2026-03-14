package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;

public record VincularUsuarioJson(
        Long tipoUsuarioId,
        Long usuarioParaAtribuirId
) {

    public static VincularUsuarioInputDTO fromInput(Long tipoUsuarioId, Long usuarioParaAtribuirId) {
        return new VincularUsuarioInputDTO(tipoUsuarioId, usuarioParaAtribuirId);
    }

}
