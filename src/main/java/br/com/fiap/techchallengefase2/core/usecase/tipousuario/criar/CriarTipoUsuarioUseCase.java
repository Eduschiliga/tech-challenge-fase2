package br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.DadosUsuarioInvalidosException;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory.obterInstancia;

@RequiredArgsConstructor
public class CriarTipoUsuarioUseCase implements CriarTipoUsuario {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Override
    public Long criar(Long usuarioLogadoId, Long restauranteId, String nomeTipo) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Dono dono = obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, restauranteId);
        validarNome(nomeTipo);

        TipoUsuario tipoUsuario = new TipoUsuario(null, restauranteId, nomeTipo);

        return tipoUsuarioGateway.salvar(tipoUsuario);
    }

    private void validarNome(String nomeTipo) {
        if (Objects.isNull(nomeTipo) || nomeTipo.isBlank()) {
            throw new DadosUsuarioInvalidosException("Nome do Tipo de usuário não pode ser vazio");
        }
    }
}
