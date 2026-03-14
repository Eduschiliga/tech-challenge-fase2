package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;

public record AtualizarSenhaJson(
        String novaSenha,
        String senhaAtual
) {

    public static AtualizarSenhaInputDTO fromInput(String novaSenha, String senhaAtual) {
        return new AtualizarSenhaInputDTO(novaSenha, senhaAtual);
    }

}
