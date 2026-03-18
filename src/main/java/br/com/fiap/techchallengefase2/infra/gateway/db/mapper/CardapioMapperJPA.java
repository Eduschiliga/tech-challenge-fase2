package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RestauranteMapperJPA.class, ItemCardapioMapperJPA.class})
public interface CardapioMapperJPA {

    CardapioEntityJPA toEntity(Cardapio cardapio);

    @Mapping(target = "itens", source = "itens")
    @Mapping(target = "restaurante", source = "restaurante")
    Cardapio toDomain(CardapioEntityJPA entity);

}
