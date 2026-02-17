package br.com.fiap.techchallengefase2.usuario.core.rule.senha;

import br.com.fiap.techchallengefase2.usuario.core.dto.AtualizarSenhaInputDTO;

import java.util.Objects;

public class ValidaSenhaValida implements RuleAtualizarSenhaUsuario {
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
}
