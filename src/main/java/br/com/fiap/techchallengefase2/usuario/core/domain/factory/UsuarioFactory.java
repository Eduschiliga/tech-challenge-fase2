package br.com.fiap.techchallengefase2.usuario.core.domain.factory;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosUsuarioInputDTO;

import java.util.ArrayList;

/**
 * Factory para criar instâncias modificadas de UsuarioBase.
 * Responsável por encapsular a lógica de atualização de dados do usuário.
 */
public class UsuarioFactory {
    public static final int TIPO_USUARIO_DONO = 0;
    public static final int TIPO_USUARIO_CLIENTE = 1;

    public static UsuarioBase atualizarDadosParciais(
            UsuarioBase usuarioAtual,
            DadosUsuarioInputDTO dadosUsuarioInputDto) {

        return criarCopiaComNovosDados(
                usuarioAtual,
                dadosUsuarioInputDto.getNome(),
                dadosUsuarioInputDto.getEmail(),
                dadosUsuarioInputDto.getLogin(),
                dadosUsuarioInputDto.getEndereco(),
                usuarioAtual.getSenha(),
                usuarioAtual.getCategoriaUsuario()
        );
    }

    private static UsuarioBase criarCopiaComNovosDados(
            UsuarioBase usuarioOriginal,
            String nome,
            String email,
            String login,
            String endereco,
            String senha,
            Integer categoriaUsuario
    ) {
        // 0 - Dono
        return switch (usuarioOriginal) {
            case null -> throw new IllegalArgumentException("Usuário base não pode ser null");

            case Dono donoOriginal when categoriaUsuario.equals(TIPO_USUARIO_DONO) -> new Dono(
                    donoOriginal.getUsuarioId(),
                    nome,
                    email,
                    login,
                    senha,
                    endereco,
                    donoOriginal.getRestaurantes()
            );

            case Cliente cliente when categoriaUsuario.equals(TIPO_USUARIO_CLIENTE) -> new Cliente(
                    cliente.getUsuarioId(),
                    nome,
                    email,
                    login,
                    senha,
                    endereco
            );

            default ->
                    throw new IllegalArgumentException("Categoria de usuário " + categoriaUsuario + " não suportada");
        };

    }

    public static UsuarioBase criarUsuario(
            Integer categoriaUsuario,
            String nome,
            String email,
            String login,
            String senha,
            String endereco
    ) {
        return switch (categoriaUsuario) {
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

            default -> {
                String mensagemErro = "Categoria de usuário " + categoriaUsuario + " não suportada";
                throw new IllegalArgumentException(mensagemErro);
            }
        };

    }
}