package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCardapioMapperJPA {

    ItemCardapioEntityJPA toEntity(ItemCardapio itemCardapio);

    @Mapping(target = "cardapioId", source = "cardapio.cardapioId")
    ItemCardapio toDomain(ItemCardapioEntityJPA entity);
}
