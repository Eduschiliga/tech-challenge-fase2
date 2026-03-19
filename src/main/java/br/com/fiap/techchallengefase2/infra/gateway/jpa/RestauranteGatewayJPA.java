package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.RestauranteMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.UsuarioNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestauranteGatewayJPA implements RestauranteGateway {

    private final RestauranteRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteMapperJPA mapper;

    @Override
    @Transactional
    public Long salvar(Restaurante restaurante) {
        RestauranteEntityJPA entity = mapper.toEntity(restaurante);

        UsuarioEntityJPA usuario = usuarioRepository.findById(restaurante.getUsuarioId())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        entity.setUsuario(usuario);

        return repository.save(entity).getRestauranteId();
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long restauranteId) {
        return repository.findById(restauranteId).map(mapper::toDomain);
    }

    @Override
    public List<Restaurante> buscarTodosPorUsuarioId(Long usuarioId) {
        return repository.findAllByUsuario_UsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Restaurante> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public void deletarPorId(Long restauranteId) {
        repository.deleteById(restauranteId);
    }
}
