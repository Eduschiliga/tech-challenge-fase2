package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarItemCardapioUseCaseTest {

    @InjectMocks
    private DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    @Mock
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;


    @Test
    void deveDeletarItemCardapioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        Dono dono = new Dono(usuarioLogadoId, "Nome", "Email", "Login", "Senha", "Endereco", new ArrayList<>());
        Cardapio cardapio = new Cardapio(1L, new Restaurante(10L, "Rest", "End", "Tipo", "Hor", 1L), new ArrayList<>(), "Menu");


        ItemCardapio itemMock = mock(ItemCardapio.class);
        when(itemMock.getCardapioId()).thenReturn(1L);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(dono);
        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)).thenReturn(itemMock);
        when(buscarCardapioPorIdUseCase.buscarPorId(1L)).thenReturn(cardapio);

        deletarItemCardapioUseCase.deletarPorId(usuarioLogadoId, itemCardapioId);

        verify(buscarItemCardapioPorIdUseCase).buscarPorId(usuarioLogadoId, itemCardapioId);
        verify(itemCardapioGateway).deletarPorId(itemCardapioId);
    }

    @Test
    void devePropagarExcecaoQuandoBuscaFalharOuSemPermissao() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        Dono dono = new Dono(usuarioLogadoId, "Nome", "Email", "Login", "Senha", "Endereco", new ArrayList<>());

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(dono);
        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId))
                .thenThrow(UsuarioNaoDonoException.class);

        assertThrows(UsuarioNaoDonoException.class, () ->
                deletarItemCardapioUseCase.deletarPorId(usuarioLogadoId, itemCardapioId)
        );

        verify(itemCardapioGateway, never()).deletarPorId(anyLong());
    }
}
