package br.com.fiap.techchallengefase2.usuario.core.dto.senha;

public class AtualizarSenhaDTO {
    private String novaSenha;
    private String senhaAtual;

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public AtualizarSenhaDTO(String senhaAtual, String novaSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }
}
