package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.ItemCardapioRepository;
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
class ItemCardapioGatewayImplTest {

    @InjectMocks
    private ItemCardapioGatewayImpl itemCardapioGateway;

    @Mock
    private ItemCardapioRepository repository;

    @Test
    void deveSalvarItemCardapioComSucesso() {
        ItemCardapio itemDomain = new ItemCardapio(1L, "Prato", "Desc", 50.0, true, "foto.png", 10L);
        ItemCardapioEntityJPA entitySalva = ItemCardapioEntityJPA.builder().itemCardapioId(1L).build();

        when(repository.save(any(ItemCardapioEntityJPA.class))).thenReturn(entitySalva);

        Long idSalvo = itemCardapioGateway.salvar(itemDomain);

        assertEquals(1L, idSalvo);
        verify(repository).save(any(ItemCardapioEntityJPA.class));
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        ItemCardapioEntityJPA entity = ItemCardapioEntityJPA.builder()
                .itemCardapioId(1L)
                .nome("Prato")
                .descricao("Desc")
                .preco(50.0)
                .disponivelApenasRestaurante(true)
                .caminhoFoto("foto.png")
                .restauranteId(10L)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<ItemCardapio> resultado = itemCardapioGateway.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getItemCardapioId(), resultado.get().getItemCardapioId());
        assertEquals(entity.getNome(), resultado.get().getNome());
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        ItemCardapioEntityJPA entity1 = ItemCardapioEntityJPA.builder().itemCardapioId(1L).restauranteId(10L).build();
        ItemCardapioEntityJPA entity2 = ItemCardapioEntityJPA.builder().itemCardapioId(2L).restauranteId(10L).build();

        when(repository.findAllByRestauranteId(10L)).thenReturn(List.of(entity1, entity2));

        List<ItemCardapio> resultados = itemCardapioGateway.buscarTodosPorRestauranteId(10L);

        assertEquals(2, resultados.size());
        assertEquals(1L, resultados.get(0).getItemCardapioId());
        assertEquals(2L, resultados.get(1).getItemCardapioId());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        itemCardapioGateway.deletarPorId(1L);
        verify(repository).deleteById(1L);
    }
}