package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

public class Cliente extends UsuarioBase {
    public Cliente(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        super(usuarioId, nome, email, login, senha, endereco);
    }
}
