package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

public abstract class UsuarioBase {
    private Long usuarioId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Integer categoriaUsuario;

    public UsuarioBase(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco,
            Integer categoriaUsuario
    ) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.categoriaUsuario = categoriaUsuario;
    }

    public Integer getCategoriaUsuario() {
        return categoriaUsuario;
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
}