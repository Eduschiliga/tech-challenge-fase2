package br.com.fiap.techchallengefase2.core.usecase.restaurante.criar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import static br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory.obterInstancia;

@RequiredArgsConstructor
public class CriarRestauranteUseCase implements CriarRestaurante {

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final RestauranteGateway restauranteGateway;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;

    @Override
    public Long criar(Long usuarioLogadoId, DadosRestauranteInputDTO dadosRestauranteInputDTO) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Dono dono = obterInstancia(usuarioBase, Dono.class);

        Restaurante restaurante = map(dadosRestauranteInputDTO, dono);

        return restauranteGateway.salvar(restaurante);
    }

    public Restaurante map(DadosRestauranteInputDTO dadosRestauranteInputDTO, Dono dono) {
        return new Restaurante(
                null,
                dadosRestauranteInputDTO.nome(),
                dadosRestauranteInputDTO.endereco(),
                dadosRestauranteInputDTO.tipoCozinha(),
                dadosRestauranteInputDTO.horarioFuncionamento(),
                dono.getUsuarioId()
        );
    }
}
