package br.com.fiap.techchallengefase2.core.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioInputDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Integer categoriaUsuario;
}
