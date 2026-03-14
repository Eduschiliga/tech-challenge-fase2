package br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseTest {

    @InjectMocks
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    @Mock
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Test
    @DisplayName("Deve atualizar o restaurante com sucesso")
    void deveAtualizarRestauranteComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Long idEsperado = 10L;

        // Instanciamos um Restaurante real para validar a mutação de estado
        Restaurante restaurante = new Restaurante(
                restauranteId,
                "Nome Antigo",
                "Endereço Antigo",
                "Cozinha Antiga",
                "08h-18h",
                usuarioLogadoId
        );

        // Usamos um DTO real com os novos dados
        DadosRestauranteInputDTO dadosDTO = new DadosRestauranteInputDTO(
                "Novo Nome",
                "Novo Endereco",
                "Japonesa",
                "19h-23h"
        );

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)).thenReturn(restaurante);
        when(restauranteGateway.salvar(restaurante)).thenReturn(idEsperado);

        // Act
        Long resultado = atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO);

        // Assert
        assertEquals(idEsperado, resultado);

        // Validamos se a própria Entidade de Domínio foi atualizada corretamente!
        assertEquals("Novo Nome", restaurante.getNome());
        assertEquals("Novo Endereco", restaurante.getEndereco());
        assertEquals("Japonesa", restaurante.getTipoCozinha());
        assertEquals("19h-23h", restaurante.getHorarioFuncionamento());

        verify(buscarRestaurantePorIdUseCase).buscarPorId(usuarioLogadoId, restauranteId);
        verify(restauranteGateway).salvar(restaurante);
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário não for do tipo Dono ou não for proprietário")
    void devePropagarExcecaoQuandoNaoForDonoOuProprietario() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosRestauranteInputDTO dadosDTO = mock(DadosRestauranteInputDTO.class);

        // Como a regra foi delegada para a busca, precisamos mockar a busca estourando o erro
        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO)
        );

        verify(restauranteGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando restaurante não for encontrado")
    void devePropagarExcecaoQuandoRestauranteInvalido() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosRestauranteInputDTO dadosDTO = mock(DadosRestauranteInputDTO.class);

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO)
        );

        verify(restauranteGateway, never()).salvar(any());
    }
}