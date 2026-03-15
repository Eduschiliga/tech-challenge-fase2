package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapperJPA {

    @Mapping(target = "restaurante", source = "restauranteId", qualifiedByName = "restauranteIdToEntity")
    @Mapping(target = "usuarios", expression = "java(new java.util.ArrayList<>())")
    TipoUsuarioEntityJPA toEntity(TipoUsuario tipoUsuario);

    @Mapping(target = "restauranteId", source = "restaurante", qualifiedByName = "entityToRestauranteId")
    TipoUsuario toDomain(TipoUsuarioEntityJPA entity);

    @Named("restauranteIdToEntity")
    default RestauranteEntityJPA restauranteIdToEntity(Long restauranteId) {
        if (restauranteId == null) return null;
        return RestauranteEntityJPA.builder().restauranteId(restauranteId).build();
    }

    @Named("entityToRestauranteId")
    default Long entityToRestauranteId(RestauranteEntityJPA entity) {
        if (entity == null) return null;
        return entity.getRestauranteId();
    }
}
