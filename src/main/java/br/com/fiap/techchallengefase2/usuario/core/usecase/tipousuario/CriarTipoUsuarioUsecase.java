package br.com.fiap.techchallengefase2.usuario.core.usecase.tipousuario;

import br.com.fiap.techchallengefase2.restaurante.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario.Dono;
import br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario.TipoUsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

public class CriarTipoUsuarioUsecase {
    private final UsuarioGateway usuarioGateway;
    private final RestauranteGateway restauranteGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;


    public CriarTipoUsuarioUsecase(
            UsuarioGateway usuarioGateway,
            RestauranteGateway restauranteGateway,
            TipoUsuarioGateway tipoUsuarioGateway
    ) {
        this.usuarioGateway = usuarioGateway;
        this.restauranteGateway = restauranteGateway;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public Long criar(Long usuarioLogadoId, Long restauranteId, TipoUsuarioBase novoTipoUsuarioBase) {
        TipoUsuarioBase tipoUsuarioBase = buscarUsuarioBasePorId(usuarioLogadoId);
        obtemRestaurantes(usuarioLogadoId, tipoUsuarioBase);

        return tipoUsuarioGateway.salvar(restauranteId, novoTipoUsuarioBase);
    }

    private void obtemRestaurantes(Long usuarioLogadoId, TipoUsuarioBase novoTipoUsuario) {
        if (novoTipoUsuario instanceof Dono dono) {
            var restaurantes = restauranteGateway.obterPorUserId(usuarioLogadoId);
            dono.adicionarRestaurantes(restaurantes);
        }
    }

    private TipoUsuarioBase buscarUsuarioBasePorId(Long usuarioBaseId) {
        return usuarioGateway.buscarPorId(usuarioBaseId)
                .orElseThrow(() -> {
                    String mensagem = "Usuário do Id " + usuarioBaseId + " não encontrado";
                    return new IllegalArgumentException(mensagem);
                });
    }


}
