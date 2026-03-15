package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardapioMapperJPA {

    CardapioEntityJPA toEntity(Cardapio cardapio);

    Cardapio toDomain(CardapioEntityJPA entity);

}
