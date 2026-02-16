package br.com.fiap.techchallengefase2.usuario.core.domain.factory;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;

import java.util.ArrayList;

/**
 * Factory para criar instâncias modificadas de UsuarioBase.
 * Responsável por encapsular a lógica de atualização de dados do usuário.
 */
public class UsuarioFactory {
    public static final int TIPO_USUARIO_DONO = 0;
    public static final int TIPO_USUARIO_CLIENTE = 1;

    /**
     * Cria uma nova instância de usuário com dados parciais atualizados.
     *
     * @param usuarioAtual            o usuário atual com dados antigos
     * @param dadosParciaisUsuarioDto usuário contendo os novos dados (parciais)
     * @return uma nova instância do usuário com os dados parciais atualizados
     */
    public static UsuarioBase atualizarDadosParciais(
            UsuarioBase usuarioAtual,
            DadosParciaisUsuarioDTO dadosParciaisUsuarioDto) {

        return criarCopiaComNovosDados(
                usuarioAtual,
                dadosParciaisUsuarioDto.getNome(),
                dadosParciaisUsuarioDto.getEmail(),
                dadosParciaisUsuarioDto.getLogin(),
                dadosParciaisUsuarioDto.getEndereco(),
                usuarioAtual.getSenha(),
                usuarioAtual.getCategoriaUsuario()
        );
    }

    /**
     * Método auxiliar privado para criar uma cópia do usuário com novos dados.
     * Este método é responsável por manter a imutabilidade do domínio.
     *
     * @param usuarioOriginal o usuário original
     * @param nome            novo nome
     * @param email           novo email
     * @param login           novo login
     * @param endereco        novo endereço
     * @param senha           nova senha
     * @return nova instância do usuário com os dados atualizados
     */
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
            String senhaCodificada,
            String endereco
    ) {
        return switch (categoriaUsuario) {
            case TIPO_USUARIO_DONO -> new Dono(
                    null,
                    nome,
                    email,
                    login,
                    senhaCodificada,
                    endereco,
                    new ArrayList<>()
            );

            case TIPO_USUARIO_CLIENTE -> new Cliente(
                    null,
                    nome,
                    email,
                    login,
                    senhaCodificada,
                    endereco
            );

            default -> {
                String mensagemErro = "Categoria de usuário " + categoriaUsuario + " não suportada";
                throw new IllegalArgumentException(mensagemErro);
            }
        };

    }
}