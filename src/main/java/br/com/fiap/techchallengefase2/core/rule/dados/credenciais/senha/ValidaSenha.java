package br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha;

import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;

import java.util.Objects;

public class ValidaSenha implements RuleAtualizarSenhaUsuario {
    @Override
    public void validar(String senhaAtual, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        String novaSenha = atualizarSenhaInputDto.getNovaSenha();

        if (Objects.isNull(novaSenha) || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser nula ou vazia");
        }

        if (novaSenha.length() < 8) {
            throw new IllegalArgumentException("Nova senha não pode ser menor que 8 caracteres");
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 2;
    }
}
