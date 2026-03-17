package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory.obterInstancia;

@Component
@RequiredArgsConstructor
public class UsuarioMapperJPA {
    private final TipoUsuarioMapperJPA tipoUsuarioMapperJPA;
    private final RestauranteMapperJPA restauranteMapperJPA;

    public UsuarioBase toDomain(UsuarioEntityJPA entity) {
        List<TipoUsuario> tipos = entity.getTipoUsuarioList().stream().map(tipoUsuarioMapperJPA::toDomain).toList();

        if (entity.getCategoria() == CategoriaUsuario.DONO) {
            List<Restaurante> restaurantes = entity.getRestauranteList().stream().map(restauranteMapperJPA::toDomain).toList();

            return new Dono(
                    entity.getUsuarioId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    entity.getEndereco(),
                    restaurantes,
                    tipos
            );
        } else {
            return new Cliente(
                    entity.getUsuarioId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    entity.getEndereco(),
                    tipos
            );
        }
    }

    public UsuarioEntityJPA toEntity(UsuarioBase domain) {
        List<TipoUsuarioEntityJPA> tipos = domain.getTipoUsuarioList()
                .stream()
                .map(tipoUsuarioMapperJPA::toEntity)
                .toList();

        if (Objects.equals(domain.getCategoria(), 0)) {
            Dono dono = obterInstancia(domain, Dono.class);

            List<RestauranteEntityJPA> restaurantes = dono.getRestaurantes()
                    .stream()
                    .map(restauranteMapperJPA::toEntity)
                    .toList();

            return new UsuarioEntityJPA(
                    domain.getUsuarioId(),
                    domain.getNome(),
                    domain.getEndereco(),
                    domain.getEmail(),
                    domain.getLogin(),
                    domain.getSenha(),
                    CategoriaUsuario.DONO,
                    restaurantes,
                    tipos
            );
        } else {
            return new UsuarioEntityJPA(
                    domain.getUsuarioId(),
                    domain.getNome(),
                    domain.getEndereco(),
                    domain.getEmail(),
                    domain.getLogin(),
                    domain.getSenha(),
                    CategoriaUsuario.CLIENTE,
                    new ArrayList<>(),
                    tipos
            );
        }
    }
}
