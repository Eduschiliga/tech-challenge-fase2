package br.com.fiap.techchallengefase2.core.rule.senha;

import br.com.fiap.techchallengefase2.core.dto.AtualizarSenhaInputDTO;

public class ValidaSenhaAtual implements RuleAtualizarSenhaUsuario {
    @Override
    public void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        if (!senhaAtual.equals(atualizarSenhaInputDto.getSenhaAtual())) {
            throw new IllegalArgumentException("Senha atual não confere");
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 1;
    }
}
