package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.categoriausuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.id.BuscarUsuarioPorIdUseCase;

import java.util.Objects;

public class AtualizarCategoriaUsuarioUseCase implements AtualizarCategoriaUsuario {
    private final UsuarioGateway usuarioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    public AtualizarCategoriaUsuarioUseCase(
            UsuarioGateway usuarioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase
    ) {
        this.usuarioGateway = usuarioGateway;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
    }

    @Override
    public Long atualizar(Long usuarioLogadoId, Integer categoriaUsuario) {
        UsuarioBase usuario = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        if (Objects.equals(usuario.getCategoriaUsuario(), categoriaUsuario)) {
            throw new IllegalArgumentException("Usuário já pertence a categoria informada");
        }

        if (categoriaUsuario < 0 || categoriaUsuario > 1) {
            throw new IllegalArgumentException("Categoria de usuário inválida");
        }

        return usuarioGateway.atualizarCategoria(usuarioLogadoId, categoriaUsuario);
    }

}
