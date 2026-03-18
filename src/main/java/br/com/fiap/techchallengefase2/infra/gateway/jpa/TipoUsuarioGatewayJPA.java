package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.TipoUsuarioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.restaurante.RestauranteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoUsuarioGatewayJPA implements TipoUsuarioGateway {

    private final TipoUsuarioRepository repository;
    private final RestauranteRepository restauranteRepository;
    private final TipoUsuarioMapperJPA mapper;

    @Override
    public Long salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntityJPA entity = mapper.toEntity(tipoUsuario);

        RestauranteEntityJPA restaurante = restauranteRepository.findById(tipoUsuario.getRestauranteId())
                .orElseThrow(RestauranteNaoEncontradoException::new);

        entity.setRestaurante(restaurante);

        return repository.save(entity).getTipoUsuarioId();
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(Long tipoUsuarioId) {
        return repository.findById(tipoUsuarioId).map(mapper::toDomain);
    }

    @Override
    public void deletarPorId(Long tipoUsuarioId) {
        repository.deleteById(tipoUsuarioId);
    }

    @Override
    public List<TipoUsuario> buscarTodosPorRestauranteId(Long restauranteId) {
        return repository.findAllByRestaurante_RestauranteId(restauranteId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
