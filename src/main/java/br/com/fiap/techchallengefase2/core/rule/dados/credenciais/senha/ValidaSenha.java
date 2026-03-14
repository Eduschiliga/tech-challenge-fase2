package br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.exception.NovaSenhaInvalidaException;

import java.util.Objects;

public class ValidaSenha implements RuleAtualizarSenhaUsuario {
    @Override
    public void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        String novaSenha = atualizarSenhaInputDto.getNovaSenha();

        if (Objects.isNull(novaSenha) || novaSenha.length() < 8) {
            throw new NovaSenhaInvalidaException();
        }

    }

    @Override
    public int getOrdemValidacao() {
        return 2;
    }
}
