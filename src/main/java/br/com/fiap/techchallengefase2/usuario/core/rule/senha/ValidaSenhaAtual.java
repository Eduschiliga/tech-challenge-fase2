package br.com.fiap.techchallengefase2.usuario.core.rule.senha;

import br.com.fiap.techchallengefase2.usuario.core.dto.senha.AtualizarSenhaDTO;

public class ValidaSenhaAtual implements RuleAtualizarSenhaUsuario {
    @Override
    public void validar(String senhaAtual, AtualizarSenhaDTO atualizarSenhaDto) {
        if (!senhaAtual.equals(atualizarSenhaDto.getSenhaAtual())) {
            throw new IllegalArgumentException("Senha atual não confere");
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 1;
    }
}
