package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;

import java.util.ArrayList;

public class UsuarioFactory {

    private UsuarioFactory() {
        throw new IllegalArgumentException("Usuario Factory não pode ser instanciada");
    }

    public static <T extends UsuarioBase> T obterInstancia(UsuarioBase usuarioBase, Class<T> tipoDestino) {
        if (tipoDestino.isInstance(usuarioBase)) {
            return tipoDestino.cast(usuarioBase);
        }

        throw new IllegalArgumentException("O usuário não é do tipo esperado: " + tipoDestino.getSimpleName());
    }


    public static UsuarioBase criarCopiaComNovosDados(
            UsuarioBase usuarioOriginal,
            String nome,
            String email,
            String login,
            String endereco
    ) {
        return switch (usuarioOriginal) {
            case null -> throw new IllegalArgumentException("Usuário base não pode ser null");

            case Dono dono -> new Dono(
                    dono.getUsuarioId(),
                    nome,
                    email,
                    login,
                    dono.getSenha(),
                    endereco,
                    dono.getRestaurantes(),
                    dono.getTipoUsuarioList()
            );

            case Cliente cliente -> new Cliente(
                    cliente.getUsuarioId(),
                    nome,
                    email,
                    login,
                    cliente.getSenha(),
                    endereco,
                    usuarioOriginal.getTipoUsuarioList()
            );

            default -> throw new IllegalArgumentException("Tipo de usuário não suportado na atualização");
        };
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

            default -> throw new IllegalArgumentException("Tipo de usuário inválido");
        };
    }
}