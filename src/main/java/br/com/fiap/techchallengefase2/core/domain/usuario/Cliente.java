package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Cliente extends UsuarioBase {

    public Cliente(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco,
            List<TipoUsuario> tipoUsuarioList
    ) {
        super(usuarioId, nome, email, login, senha, endereco, CategoriaUsuario.CLIENTE.getCodigo(), tipoUsuarioList);
    }

    public Cliente(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        super(usuarioId, nome, email, login, senha, endereco, CategoriaUsuario.CLIENTE.getCodigo(), new ArrayList<>());
    }

}
