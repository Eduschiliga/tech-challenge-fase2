package br.com.fiap.techchallengefase2.infra.gateway.db.repository;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<RestauranteEntityJPA, Long> {
    List<RestauranteEntityJPA> findAllByUsuarioId(Long usuarioId);
}