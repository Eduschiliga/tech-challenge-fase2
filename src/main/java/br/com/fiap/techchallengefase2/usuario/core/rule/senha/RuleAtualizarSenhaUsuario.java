package br.com.fiap.techchallengefase2.usuario.core.rule.senha;

import br.com.fiap.techchallengefase2.usuario.core.dto.senha.AtualizarSenhaDTO;

public interface RuleAtualizarSenhaUsuario {

    void validar(String senhaAtual, AtualizarSenhaDTO atualizarSenhaDto);

    default int getOrdemValidacao() {
        return 100;
    }
}
