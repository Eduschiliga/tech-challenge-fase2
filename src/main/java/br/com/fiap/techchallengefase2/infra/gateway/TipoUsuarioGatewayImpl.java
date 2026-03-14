package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoUsuarioGatewayImpl implements TipoUsuarioGateway {

    private final TipoUsuarioRepository repository;

    @Override
    public Long salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntityJPA entity = TipoUsuarioEntityJPA.builder()
                .tipoUsuarioId(tipoUsuario.getTipoUsuarioId())
                .nome(tipoUsuario.getNome())
                .restaurante(RestauranteEntityJPA.builder().restauranteId(tipoUsuario.getRestauranteId()).build())
                .usuario(null)
                .build();

        return repository.save(entity).getTipoUsuarioId();
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(Long tipoUsuarioId) {
        return repository.findById(tipoUsuarioId).map(this::toDomain);
    }

    @Override
    public void deletarPorId(Long tipoUsuarioId) {
        repository.deleteById(tipoUsuarioId);
    }

    @Override
    public List<TipoUsuario> buscarTodosPorRestauranteId(Long restauranteId) {
        return repository.findAllByRestaurante_RestauranteId(restauranteId).stream()
                .map(this::toDomain)
                .toList();
    }

    private TipoUsuario toDomain(TipoUsuarioEntityJPA entity) {
        return new TipoUsuario(
                entity.getTipoUsuarioId(),
                entity.getRestaurante() != null ? entity.getRestaurante().getRestauranteId() : null,
                entity.getNome()
        );
    }
}