package br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.exception.SenhaAtualIncorretaException;

public class ValidaSenhaAtual implements RuleSenhaUsuario {
    @Override
    public void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        if (!senhaAtual.equals(atualizarSenhaInputDto.getSenhaAtual())) {
            throw new SenhaAtualIncorretaException();
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 1;
    }
}
