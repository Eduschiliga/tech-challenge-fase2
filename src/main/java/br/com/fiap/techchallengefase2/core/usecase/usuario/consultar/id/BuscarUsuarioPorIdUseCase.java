package br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.TipoUsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCase implements BuscarUsuarioPorId {
    private final UsuarioGateway usuarioGateway;

    @Override
    public UsuarioBase buscarPorId(Long usuarioId) {
        UsuarioBase usuarioBase = usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        return obterInstanciaDeAcordoComACategoria(usuarioBase);
    }

    private UsuarioBase obterInstanciaDeAcordoComACategoria(UsuarioBase usuarioBase) {
        if (usuarioBase.getCategoriaUsuario().equals(0)) {
            return UsuarioFactory.obterInstancia(usuarioBase, Dono.class);
        }

        if (usuarioBase.getCategoriaUsuario().equals(1)) {
            return UsuarioFactory.obterInstancia(usuarioBase, Cliente.class);
        }

        throw new TipoUsuarioNaoEncontradoException();
    }
}
