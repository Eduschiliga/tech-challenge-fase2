package br.com.fiap.techchallengefase2.infra.gateway.db.repository;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntityJPA, Long> {
    List<TipoUsuarioEntityJPA> findAllByRestaurante_RestauranteId(Long restauranteId);

    @Query("SELECT t FROM TipoUsuarioEntityJPA t JOIN t.usuarios u WHERE u.usuarioId = :usuarioId")
    List<TipoUsuarioEntityJPA> findAllByUsuarios_UsuarioId(@Param("usuarioId") Long usuarioId);
}