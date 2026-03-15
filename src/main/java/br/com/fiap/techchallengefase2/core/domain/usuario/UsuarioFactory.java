package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.exception.CategoriaInvalidaException;

import java.util.ArrayList;

public class UsuarioFactory {

    private UsuarioFactory() {
        throw new IllegalArgumentException("Usuario Factory não pode ser instanciada");
    }

    public static <T extends UsuarioBase> T obterInstancia(UsuarioBase usuarioBase, Class<T> tipoDestino) {
        if (tipoDestino.isInstance(usuarioBase)) {
            return tipoDestino.cast(usuarioBase);
        }

        throw new CategoriaInvalidaException();
    }

    public static UsuarioBase obterInstanciaDeAcordoComACategoria(UsuarioBase usuarioBase) {
        if (usuarioBase.getCategoria().equals(0)) {
            return obterInstancia(usuarioBase, Dono.class);
        }

        if (usuarioBase.getCategoria().equals(1)) {
            return obterInstancia(usuarioBase, Cliente.class);
        }

        throw new CategoriaInvalidaException();
    }

    public static UsuarioBase criarUsuario(
            Integer categoriaUsuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        CategoriaUsuario categoria = CategoriaUsuario.fromCodigo(categoriaUsuarioId);

        return switch (categoria) {
            case DONO -> new Dono(
                    null,
                    nome,
                    email,
                    login,
                    senha,
                    endereco,
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            case CLIENTE -> new Cliente(
                    null,
                    nome,
                    email,
                    login,
                    senha,
                    endereco,
                    new ArrayList<>()
            );
        };
    }


}