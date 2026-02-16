package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;

import java.util.Objects;

public class ValidaSePossuiLogin implements RuleDadosUsuario {
    @Override
    public void validar(DadosParciaisUsuarioDTO dadosParciaisDto) {
        if (Objects.isNull(dadosParciaisDto.getLogin()) || dadosParciaisDto.getLogin().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
