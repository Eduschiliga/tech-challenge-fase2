package br.com.fiap.techchallengefase2.infra.gateway.db.repository;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntityJPA, Long> {
    List<ItemCardapioEntityJPA> findAllByRestauranteId(Long restauranteId);
}