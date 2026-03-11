package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestauranteFactoryTest {

    @Test
    @DisplayName("Deve criar um novo restaurante atualizado com os dados do DTO mantendo os IDs originais")
    void deveAtualizarRestaurante() {
        Restaurante restauranteAntigo = mock(Restaurante.class);
        when(restauranteAntigo.getRestauranteId()).thenReturn(10L);
        when(restauranteAntigo.getUsuarioId()).thenReturn(1L);

        DadosRestauranteInputDTO dto = mock(DadosRestauranteInputDTO.class);
        when(dto.nome()).thenReturn("Restaurante Atualizado");
        when(dto.endereco()).thenReturn("Nova Rua");
        when(dto.tipoCozinha()).thenReturn("Italiana");
        when(dto.horarioFuncionamento()).thenReturn("10h-22h");

        Restaurante restauranteNovo = RestauranteFactory.atualizar(
                restauranteAntigo.getRestauranteId(),
                dto.nome(),
                dto.endereco(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                restauranteAntigo.getUsuarioId()
        );

        assertNotNull(restauranteNovo);
        assertEquals(10L, restauranteNovo.getRestauranteId());
        assertEquals(1L, restauranteNovo.getUsuarioId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar instanciar a factory")
    void deveLancarExcecaoAoInstanciarFactory() throws NoSuchMethodException {
        Constructor<RestauranteFactory> constructor = RestauranteFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
        assertEquals("Restaurante Factory não pode ser instanciada", exception.getCause().getMessage());
    }
}