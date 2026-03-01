package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;

import java.util.ArrayList;

public class UsuarioFactory {
    public static final int TIPO_USUARIO_DONO = 0;
    public static final int TIPO_USUARIO_CLIENTE = 1;

    public static  <T extends UsuarioBase> T obterInstancia(UsuarioBase usuarioBase, Class<T> tipoDestino) {
        if (tipoDestino.isInstance(usuarioBase)) {
            return tipoDestino.cast(usuarioBase);
        }

        throw new IllegalArgumentException("O usuário não é do tipo esperado: " + tipoDestino.getSimpleName());
    }

    public static <T extends UsuarioBase> T atualizarDadosParciais(
            T usuarioAtual,
            DadosUsuarioInputDTO dadosUsuarioInputDto
    ) {

        return (T) criarCopiaComNovosDados(
                usuarioAtual,
                dadosUsuarioInputDto.getNome(),
                dadosUsuarioInputDto.getEmail(),
                dadosUsuarioInputDto.getLogin(),
                dadosUsuarioInputDto.getEndereco()
        );
    }

    private static UsuarioBase criarCopiaComNovosDados(
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
                    dono.getRestaurantes()
            );

            case Cliente cliente -> new Cliente(
                    cliente.getUsuarioId(),
                    nome,
                    email,
                    login,
                    cliente.getSenha(),
                    endereco
            );

            default -> throw new IllegalArgumentException("Tipo de usuário não suportado na atualização");
        };
    }

    public static <T extends UsuarioBase> T criarUsuario(
            Integer categoriaUsuario,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        return (T) switch (categoriaUsuario) {
            case TIPO_USUARIO_DONO -> new Dono(
                    null,
                    nome,
                    email,
                    login,
                    senha,
                    endereco,
                    new ArrayList<>()
            );

            case TIPO_USUARIO_CLIENTE -> new Cliente(
                    null,
                    nome,
                    email,
                    login,
                    senha,
                    endereco
            );

            default -> throw new IllegalArgumentException("Categoria de usuário " + categoriaUsuario + " não suportada");
        };
    }
}