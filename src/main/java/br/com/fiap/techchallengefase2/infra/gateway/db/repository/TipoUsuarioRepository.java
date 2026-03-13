package br.com.fiap.techchallengefase2.infra.gateway.db.repository;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntityJPA, Long> {
    List<TipoUsuarioEntityJPA> findAllByRestauranteId(Long restauranteId);

    List<TipoUsuarioEntityJPA> findAllByUsuarioId(Long usuarioId);
}