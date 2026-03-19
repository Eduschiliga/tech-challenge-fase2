package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoUsuarioMapperJPA {

    @Mapping(target = "restaurante.restauranteId", source = "restauranteId")
    TipoUsuarioEntityJPA toEntity(TipoUsuario tipoUsuario);

    @Mapping(target = "restauranteId", source = "restaurante.restauranteId")
    TipoUsuario toDomain(TipoUsuarioEntityJPA entity);
}
