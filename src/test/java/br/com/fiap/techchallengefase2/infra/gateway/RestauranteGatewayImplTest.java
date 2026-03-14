package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteGatewayImplTest {

    @InjectMocks
    private RestauranteGatewayImpl restauranteGateway;

    @Mock
    private RestauranteRepository repository;

    @Test
    void deveSalvarRestauranteComSucesso() {
        Restaurante restaurante = new Restaurante(1L, "Rest", "End", "Italiana", "08-18", 100L);
        RestauranteEntityJPA entitySalva = RestauranteEntityJPA.builder().restauranteId(1L).build();

        when(repository.save(any(RestauranteEntityJPA.class))).thenReturn(entitySalva);

        Long idSalvo = restauranteGateway.salvar(restaurante);

        assertEquals(1L, idSalvo);
        verify(repository).save(any(RestauranteEntityJPA.class));
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        RestauranteEntityJPA entity = RestauranteEntityJPA.builder()
                .restauranteId(1L)
                .nome("Rest")
                .endereco("End")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("08-18")
                .usuario(UsuarioEntityJPA.builder().usuarioId(100L).build())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Restaurante> resultado = restauranteGateway.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getRestauranteId(), resultado.get().getRestauranteId());
        assertEquals(entity.getNome(), resultado.get().getNome());
    }

    @Test
    void deveBuscarTodosPorUsuarioId() {
        RestauranteEntityJPA entity1 = RestauranteEntityJPA.builder().restauranteId(1L).usuario(UsuarioEntityJPA.builder().usuarioId(100L).build()).build();
        RestauranteEntityJPA entity2 = RestauranteEntityJPA.builder().restauranteId(2L).usuario(UsuarioEntityJPA.builder().usuarioId(100L).build()).build();

        when(repository.findAllByUsuario_UsuarioId(100L)).thenReturn(List.of(entity1, entity2));

        List<Restaurante> resultados = restauranteGateway.buscarTodosPorUsuarioId(100L);

        assertEquals(2, resultados.size());
        assertEquals(1L, resultados.get(0).getRestauranteId());
        assertEquals(2L, resultados.get(1).getRestauranteId());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        restauranteGateway.deletarPorId(1L);
        verify(repository).deleteById(1L);
    }
}