package br.com.fiap.techchallengefase2.usuario.core.dto;

public class CriarUsuarioDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Integer categoriaUsuario;

    public Integer getCategoriaUsuario() {
        return categoriaUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public DadosParciaisUsuarioDTO toDadosParciaisUsuarioDTO(){
        return new DadosParciaisUsuarioDTO(
                getNome(),
                getEmail(),
                getLogin(),
                getEndereco()
        );
    }
}
