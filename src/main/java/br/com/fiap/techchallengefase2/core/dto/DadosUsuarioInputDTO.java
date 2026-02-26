package br.com.fiap.techchallengefase2.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosUsuarioInputDTO {
    private String nome;
    private String email;
    private String login;
    private String endereco;
}
