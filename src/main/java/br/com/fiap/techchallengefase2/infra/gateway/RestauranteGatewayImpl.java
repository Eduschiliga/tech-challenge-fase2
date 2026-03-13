package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestauranteGatewayImpl implements RestauranteGateway {

    private final RestauranteRepository repository;

    @Override
    public Long salvar(Restaurante restaurante) {
        RestauranteEntityJPA entity = RestauranteEntityJPA.builder()
                .restauranteId(restaurante.getRestauranteId())
                .nome(restaurante.getNome())
                .endereco(restaurante.getEndereco())
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .usuarioId(restaurante.getUsuarioId())
                .build();

        return repository.save(entity).getRestauranteId();
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long restauranteId) {
        return repository.findById(restauranteId).map(this::toDomain);
    }

    @Override
    public List<Restaurante> buscarTodosPorUsuarioId(Long usuarioId) {
        return repository.findAllByUsuarioId(usuarioId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long restauranteId) {
        repository.deleteById(restauranteId);
    }

    private Restaurante toDomain(RestauranteEntityJPA entity) {
        return new Restaurante(
                entity.getRestauranteId(),
                entity.getNome(),
                entity.getEndereco(),
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getUsuarioId()
        );
    }
}