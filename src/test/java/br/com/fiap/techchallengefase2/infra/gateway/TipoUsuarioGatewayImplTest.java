package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
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
class TipoUsuarioGatewayImplTest {

    @InjectMocks
    private TipoUsuarioGatewayImpl tipoUsuarioGateway;

    @Mock
    private TipoUsuarioRepository repository;

    @Test
    void deveSalvarTipoUsuarioComSucesso() {
        TipoUsuario tipoUsuario = new TipoUsuario(1L, 10L, "Garçom");
        TipoUsuarioEntityJPA entitySalva = TipoUsuarioEntityJPA.builder().tipoUsuarioId(1L).build();

        when(repository.save(any(TipoUsuarioEntityJPA.class))).thenReturn(entitySalva);

        Long idSalvo = tipoUsuarioGateway.salvar(tipoUsuario);

        assertEquals(1L, idSalvo);
        verify(repository).save(any(TipoUsuarioEntityJPA.class));
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        TipoUsuarioEntityJPA entity = TipoUsuarioEntityJPA.builder()
                .tipoUsuarioId(1L)
                .nome("Garçom")
                .restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<TipoUsuario> resultado = tipoUsuarioGateway.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getTipoUsuarioId(), resultado.get().getTipoUsuarioId());
        assertEquals(entity.getNome(), resultado.get().getNome());
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        TipoUsuarioEntityJPA entity1 = TipoUsuarioEntityJPA.builder().tipoUsuarioId(1L).restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build()).build();
        TipoUsuarioEntityJPA entity2 = TipoUsuarioEntityJPA.builder().tipoUsuarioId(2L).restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build()).build();

        when(repository.findAllByRestaurante_RestauranteId(10L)).thenReturn(List.of(entity1, entity2));

        List<TipoUsuario> resultados = tipoUsuarioGateway.buscarTodosPorRestauranteId(10L);

        assertEquals(2, resultados.size());
        assertEquals(1L, resultados.get(0).getTipoUsuarioId());
        assertEquals(2L, resultados.get(1).getTipoUsuarioId());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        tipoUsuarioGateway.deletarPorId(1L);
        verify(repository).deleteById(1L);
    }
}