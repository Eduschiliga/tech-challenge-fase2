package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
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
class AtualizarItemCardapioUseCaseTest {

    @InjectMocks
    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    @Mock
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Novo Prato", "Nova Descricao", 60.0, false, "/nova-foto.png");

        ItemCardapio itemMock = mock(ItemCardapio.class);

        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)).thenReturn(itemMock);
        when(itemCardapioGateway.salvar(any(ItemCardapio.class))).thenReturn(itemCardapioId);

        Long resultado = atualizarItemCardapioUseCase.atualizar(usuarioLogadoId, itemCardapioId, dados);

        assertEquals(itemCardapioId, resultado);
        verify(itemMock).atualizarDados(dados.nome(), dados.descricao(), dados.preco(), dados.disponivelApenasRestaurante(), dados.caminhoFoto());
        verify(itemCardapioGateway).salvar(itemMock);
    }

    @Test
    void devePropagarExcecaoQuandoBuscaFalharOuSemPermissao() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Novo", "Desc", 60.0, false, "/foto.png");

        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId))
                .thenThrow(UsuarioNaoDonoException.class);

        assertThrows(UsuarioNaoDonoException.class, () ->
                atualizarItemCardapioUseCase.atualizar(usuarioLogadoId, itemCardapioId, dados)
        );

        verify(itemCardapioGateway, never()).salvar(any());
    }
}