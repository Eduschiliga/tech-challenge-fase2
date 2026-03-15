package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestauranteMapperJPA {

    RestauranteEntityJPA toEntity(Restaurante restaurante);

    @Mapping(target = "usuarioId", source = "usuario.usuarioId")
    Restaurante toDomain(RestauranteEntityJPA entity);
}
