package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

import java.util.Objects;

public abstract class UsuarioBase {
    protected Long usuarioId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;

    public UsuarioBase(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
    }

    public Long getUsuarioId() {
        return usuarioId;
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

    public void atribuirSenhaCodificada(String senhaCodificada) {
        if (Objects.isNull(senhaCodificada) || senhaCodificada.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.senha = senhaCodificada;
    }

    public void atualizarDadosParciais(UsuarioBase usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.endereco = usuario.getEndereco();
        this.login = usuario.getLogin();
    }
}
