package br.com.fiap.techchallengefase2.core.rule.senha;

import br.com.fiap.techchallengefase2.core.dto.AtualizarSenhaInputDTO;

public interface RuleAtualizarSenhaUsuario {

    void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto);

    int getOrdemValidacao();
}
