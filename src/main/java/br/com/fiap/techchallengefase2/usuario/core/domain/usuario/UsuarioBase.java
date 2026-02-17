package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UsuarioBase {
    private Long usuarioId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Integer categoriaUsuario;

    public void atribuirSenhaCodificada(String senhaCodificada) {
        this.senha = senhaCodificada;
    }
}