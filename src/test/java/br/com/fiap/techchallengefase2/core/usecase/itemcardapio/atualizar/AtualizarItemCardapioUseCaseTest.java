package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioUseCaseTest {

    @Mock
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    @Mock
    private ItemCardapioGateway itemCardapioGateway;
    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;
    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @Mock
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @InjectMocks
    private AtualizarItemCardapioUseCase useCase;

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 10L;
        Long cardapioId = 100L;
        Long restauranteId = 1000L;

        Dono dono = new Dono(usuarioLogadoId, "Dono", "dono@email.com", "dono_login", "senha", "end", new ArrayList<>(), new ArrayList<>());
        Restaurante restaurante = new Restaurante(restauranteId, "Restaurante do Dono", "end", "cozinha", "horario", usuarioLogadoId);
        Cardapio cardapio = new Cardapio(cardapioId, restaurante, new ArrayList<>(), "Cardapio Principal");
        ItemCardapio itemCardapio = spy(new ItemCardapio(itemCardapioId, "Item Original", "Desc Original", 50.0, false, "foto.jpg", cardapioId));

        DadosItemCardapioInputDTO dadosInput = new DadosItemCardapioInputDTO(
                "Item Atualizado",
                "Desc Atualizada",
                75.5,
                true,
                "foto_nova.jpg"
        );

        // Given
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(dono);
        doNothing().when(validaSeUsuarioDono).validar(dono);

        when(buscarItemCardapioPorIdUseCase.buscarPorId(anyLong(), eq(itemCardapioId))).thenReturn(itemCardapio);
        when(buscarCardapioPorIdUseCase.buscarPorId(cardapioId)).thenReturn(cardapio);
        doNothing().when(validaSeUsuarioDonoRestaurante).validar(dono, restauranteId);

        when(itemCardapioGateway.salvar(itemCardapio)).thenReturn(itemCardapioId);

        // When
        Long resultId = useCase.atualizar(usuarioLogadoId, itemCardapioId, dadosInput);

        // Then
        assertNotNull(resultId);
        assertEquals(itemCardapioId, resultId);

        verify(itemCardapio, times(1)).atualizarDados(
                dadosInput.nome(),
                dadosInput.descricao(),
                dadosInput.preco(),
                dadosInput.disponivelApenasRestaurante(),
                dadosInput.caminhoFoto()
        );
        assertEquals("Item Atualizado", itemCardapio.getNome());

        verify(validaSeUsuarioDono, times(1)).validar(dono);
        verify(validaSeUsuarioDonoRestaurante, times(1)).validar(dono, restauranteId);
        verify(itemCardapioGateway, times(1)).salvar(itemCardapio);
    }

    @Test
    void deveLancarExcecao_quandoUsuarioNaoForDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 10L;
        Dono dono = new Dono(usuarioLogadoId, "Dono", "dono@email.com", "dono_login", "senha", "end", new ArrayList<>(), new ArrayList<>());
        DadosItemCardapioInputDTO dadosInput = new DadosItemCardapioInputDTO(
                "Item Atualizado",
                "Desc Atualizada",
                75.5,
                true,
                "foto_nova.jpg"
        );

        // Given
        when(buscarUsuarioPorIdUseCase.buscarPorId(anyLong())).thenReturn(dono);
        doThrow(new UsuarioNaoDonoException()).when(validaSeUsuarioDono).validar(dono);

        // When & Then
        assertThrows(UsuarioNaoDonoException.class, () -> useCase.atualizar(usuarioLogadoId, itemCardapioId, dadosInput));
        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecao_quandoDonoNaoForDonoDoRestaurante() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 10L;
        Long cardapioId = 100L;
        Long restauranteId = 1000L;

        Dono dono = new Dono(usuarioLogadoId, "Dono", "dono@email.com", "dono_login", "senha", "end", new ArrayList<>(), new ArrayList<>());
        Restaurante restaurante = new Restaurante(restauranteId, "Restaurante do Dono", "end", "cozinha", "horario", usuarioLogadoId);
        Cardapio cardapio = new Cardapio(cardapioId, restaurante, new ArrayList<>(), "Cardapio Principal");
        ItemCardapio itemCardapio = new ItemCardapio(itemCardapioId, "Item Original", "Desc Original", 50.0, false, "foto.jpg", cardapioId);
        DadosItemCardapioInputDTO dadosInput = new DadosItemCardapioInputDTO(
                "Item Atualizado",
                "Desc Atualizada",
                75.5,
                true,
                "foto_nova.jpg"
        );

        // Given
        when(buscarUsuarioPorIdUseCase.buscarPorId(anyLong())).thenReturn(dono);
        when(buscarItemCardapioPorIdUseCase.buscarPorId(anyLong(), eq(itemCardapioId))).thenReturn(itemCardapio);
        when(buscarCardapioPorIdUseCase.buscarPorId(cardapioId)).thenReturn(cardapio);
        doThrow(new UsuarioNaoDonoException()).when(validaSeUsuarioDonoRestaurante).validar(dono, restauranteId);

        // When & Then
        assertThrows(UsuarioNaoDonoException.class, () -> useCase.atualizar(usuarioLogadoId, itemCardapioId, dadosInput));
        verify(itemCardapioGateway, never()).salvar(any());
    }
}