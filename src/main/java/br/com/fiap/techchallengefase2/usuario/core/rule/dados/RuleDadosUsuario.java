package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;

public interface RuleDadosUsuario {

    void validar(DadosParciaisUsuarioDTO dadosParciaisDto);
}
