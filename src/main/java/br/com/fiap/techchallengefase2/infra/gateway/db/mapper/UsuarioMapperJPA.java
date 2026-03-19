package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioMapperJPA {

    private final TipoUsuarioMapperJPA tipoUsuarioMapperJPA;
    private final RestauranteMapperJPA restauranteMapperJPA;

    public UsuarioBase toDomain(UsuarioEntityJPA entity) {
        List<TipoUsuario> tipos = entity.getTipoUsuarioList() != null ?
                entity.getTipoUsuarioList().stream().map(tipoUsuarioMapperJPA::toDomain).collect(Collectors.toCollection(ArrayList::new)) : List.of();

        if (entity.getCategoria() == CategoriaUsuario.DONO) {
            List<Restaurante> restaurantes = entity.getRestauranteList() != null ?
                    entity.getRestauranteList().stream().map(restauranteMapperJPA::toDomain).collect(Collectors.toCollection(ArrayList::new)) : List.of();

            return new Dono(
                    entity.getUsuarioId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    entity.getEndereco(),
                    restaurantes,
                    tipos
            );
        }

        return new Cliente(
                entity.getUsuarioId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getEndereco(),
                tipos
        );
    }

    public UsuarioEntityJPA toEntity(UsuarioBase domain) {
        Set<TipoUsuarioEntityJPA> tipos = domain.getTipoUsuarioList() != null ?
                domain.getTipoUsuarioList()
                        .stream()
                        .map(tipoUsuarioMapperJPA::toEntity)
                        .collect(Collectors.toSet()) : new HashSet<>();

        if (domain instanceof Dono dono) {
            Set<RestauranteEntityJPA> restaurantes = dono.getRestaurantes() != null ?
                    dono.getRestaurantes()
                            .stream()
                            .map(restauranteMapperJPA::toEntity)
                            .collect(Collectors.toSet()) : new HashSet<>();

            return new UsuarioEntityJPA(
                    domain.getUsuarioId(),
                    domain.getNome(),
                    domain.getEndereco(),
                    domain.getEmail(),
                    domain.getLogin(),
                    domain.getSenha(),
                    CategoriaUsuario.DONO,
                    restaurantes,
                    tipos
            );
        }

        return new UsuarioEntityJPA(
                domain.getUsuarioId(),
                domain.getNome(),
                domain.getEndereco(),
                domain.getEmail(),
                domain.getLogin(),
                domain.getSenha(),
                CategoriaUsuario.CLIENTE,
                new HashSet<>(),
                tipos
        );
    }
}