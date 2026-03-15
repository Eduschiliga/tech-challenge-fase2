package br.com.fiap.techchallengefase2.infra.gateway.db.repository;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntityJPA, Long> {
    Optional<UsuarioEntityJPA> findByLogin(String login);

    Optional<UsuarioEntityJPA> findByEmail(String email);

    @Query("SELECT DISTINCT u FROM UsuarioEntityJPA u " +
           "LEFT JOIN FETCH u.tipoUsuarioList " +
           "LEFT JOIN FETCH u.restauranteList " +
           "WHERE u.usuarioId = :usuarioId")
    Optional<UsuarioEntityJPA> findByIdWithRelacionamentos(@Param("usuarioId") Long usuarioId);

    @Query("SELECT DISTINCT u FROM UsuarioEntityJPA u " +
           "LEFT JOIN FETCH u.tipoUsuarioList " +
           "LEFT JOIN FETCH u.restauranteList")
    List<UsuarioEntityJPA> findAllWithRelacionamentos();
}