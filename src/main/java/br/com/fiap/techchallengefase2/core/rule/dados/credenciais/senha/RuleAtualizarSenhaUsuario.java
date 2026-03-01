package br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;

public interface RuleAtualizarSenhaUsuario {

    void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto);

    int getOrdemValidacao();
}
