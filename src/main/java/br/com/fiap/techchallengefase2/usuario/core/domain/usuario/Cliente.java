package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

import static br.com.fiap.techchallengefase2.usuario.core.domain.factory.UsuarioFactory.TIPO_USUARIO_CLIENTE;

public class Cliente extends UsuarioBase {
    public Cliente(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        super(usuarioId, nome, email, login, senha, endereco, TIPO_USUARIO_CLIENTE);
    }
}
