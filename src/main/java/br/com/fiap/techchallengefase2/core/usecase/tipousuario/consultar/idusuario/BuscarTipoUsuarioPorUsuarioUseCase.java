package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuscarTipoUsuarioPorUsuarioUseCase implements BuscarTipoUsuarioPorUsuario {

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;

    @Override
    public List<TipoUsuario> buscarPorUsuario(Long usuarioLogadoId, Long usuarioId) {
        UsuarioBase usuarioLogado = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioLogado);
        Dono donoLogado = UsuarioFactory.obterInstancia(usuarioLogado, Dono.class);

        UsuarioBase usuarioBuscado = buscarUsuarioPorIdUseCase.buscarPorId(usuarioId);

        List<TipoUsuario> tiposDoUsuarioBuscado = usuarioBuscado.getTipoUsuarioList();
        if (tiposDoUsuarioBuscado == null) {
            return Collections.emptyList();
        }

        return tiposDoUsuarioBuscado.stream()
                .filter(tipoUsuario -> donoLogado.isProprietario(tipoUsuario.getRestauranteId()))
                .collect(Collectors.toList());
    }
}
