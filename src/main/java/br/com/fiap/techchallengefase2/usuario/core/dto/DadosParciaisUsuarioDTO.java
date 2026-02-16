package br.com.fiap.techchallengefase2.usuario.core.dto;

public class DadosParciaisUsuarioDTO {
    private String nome;
    private String email;
    private String login;
    private String endereco;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getEndereco() {
        return endereco;
    }

    public DadosParciaisUsuarioDTO(String nome, String email, String login, String endereco) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.endereco = endereco;
    }
}
