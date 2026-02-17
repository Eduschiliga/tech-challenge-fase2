package br.com.fiap.techchallengefase2.usuario.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarSenhaInputDTO {
    private String novaSenha;
    private String senhaAtual;
}
