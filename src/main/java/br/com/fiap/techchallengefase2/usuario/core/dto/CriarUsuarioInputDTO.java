package br.com.fiap.techchallengefase2.usuario.core.dto;

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

    public DadosUsuarioInputDTO toDadosParciaisUsuarioDTO(){
        return new DadosUsuarioInputDTO(
                getNome(),
                getEmail(),
                getLogin(),
                getEndereco()
        );
    }
}
