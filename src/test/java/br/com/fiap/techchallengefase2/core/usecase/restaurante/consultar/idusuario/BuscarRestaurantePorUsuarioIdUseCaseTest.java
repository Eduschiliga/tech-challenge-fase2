package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.idusuario;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorUsuarioIdUseCaseTest {

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscarRestaurantePorUsuarioIdUseCase buscarRestaurantePorUsuarioIdUseCase;

    @Test
    void buscarTodos_DeveRetornarListaDeRestaurantesQuandoUsuarioForValido() {
        Long usuarioLogadoId = 1L;
        UsuarioBase usuarioBase = mock(UsuarioBase.class);
        List<Restaurante> restaurantesEsperados = List.of(mock(Restaurante.class));

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioBase);
        when(restauranteGateway.buscarTodosPorUsuarioId(usuarioLogadoId)).thenReturn(restaurantesEsperados);

        List<Restaurante> result = buscarRestaurantePorUsuarioIdUseCase.buscarTodos(usuarioLogadoId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioLogadoId);
        verify(validaSeUsuarioDono).validar(usuarioBase);
        verify(restauranteGateway).buscarTodosPorUsuarioId(usuarioLogadoId);
    }
}