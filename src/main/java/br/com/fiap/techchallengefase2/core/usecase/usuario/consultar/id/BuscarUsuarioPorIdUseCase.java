package br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import static br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory.obterInstanciaDeAcordoComACategoria;

@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCase implements BuscarUsuarioPorId {
    private final UsuarioGateway usuarioGateway;

    @Override
    public UsuarioBase buscarPorId(Long usuarioId) {
        UsuarioBase usuarioBase = usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        return obterInstanciaDeAcordoComACategoria(usuarioBase);
    }
}
