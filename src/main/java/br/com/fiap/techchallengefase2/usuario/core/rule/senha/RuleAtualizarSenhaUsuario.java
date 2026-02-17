package br.com.fiap.techchallengefase2.usuario.core.rule.senha;

import br.com.fiap.techchallengefase2.usuario.core.dto.AtualizarSenhaInputDTO;

public interface RuleAtualizarSenhaUsuario {

    void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto);

    default int getOrdemValidacao() {
        return 100;
    }
}
