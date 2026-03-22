package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.ItemCardapioController;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.util.container.AbstractContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemCardapioRestControllerIT extends AbstractContainer {

    @TestConfiguration
    static class ObjectMapperConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private TokenGateway tokenGateway;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ItemCardapioController itemCardapioController;

    private final Long HEADER_USUARIO_LOGADO_ID = 99L;
    private String tokenAutenticacao;

    @BeforeEach
    void setUp() {
        tokenAutenticacao = "Bearer " + tokenGateway.gerarToken(HEADER_USUARIO_LOGADO_ID);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Hambúrguer Artesanal",
                "descricao", "Pão brioche, blend 180g, queijo cheddar",
                "preco", 35.50
        );

        Long cardapioId = 10L;

        when(itemCardapioController.criar(eq(HEADER_USUARIO_LOGADO_ID), eq(cardapioId), any(DadosItemCardapioInputDTO.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/itens-cardapio/cardapios/{cardapioId}", cardapioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(itemCardapioController).criar(eq(HEADER_USUARIO_LOGADO_ID),eq(cardapioId), any(DadosItemCardapioInputDTO.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Hambúrguer Artesanal Duplo",
                "descricao", "Pão brioche, 2 blends 180g, queijo cheddar duplo",
                "preco", 45.00
        );

        Long itemId = 1L;

        when(itemCardapioController.atualizar(eq(HEADER_USUARIO_LOGADO_ID), eq(itemId), any(DadosItemCardapioInputDTO.class)))
                .thenReturn(itemId);

        mockMvc.perform(put("/itens-cardapio/{itemId}", itemId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(itemCardapioController).atualizar(eq(HEADER_USUARIO_LOGADO_ID), eq(itemId), any(DadosItemCardapioInputDTO.class));
    }
    @Test
    void buscarPorId_DeveRetornarStatusOkEItemCardapio() throws Exception {
        Long itemId = 1L;
        ItemCardapioOutputDTO outputMock = new ItemCardapioOutputDTO(
                itemId,
                "Hambúrguer Artesanal",
                "Pão brioche, blend 180g",
                35.50,
                true,
                "foto",
                1L
        );

        when(itemCardapioController.buscarPorId(HEADER_USUARIO_LOGADO_ID, itemId)).thenReturn(outputMock);

        mockMvc.perform(get("/itens-cardapio/{itemId}", itemId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemCardapioId").value(itemId))
                .andExpect(jsonPath("$.nome").value("Hambúrguer Artesanal"))
                .andExpect(jsonPath("$.preco").value(35.50));

        // CORREÇÃO: Usando eq() para os dois parâmetros
        verify(itemCardapioController).buscarPorId(eq(HEADER_USUARIO_LOGADO_ID), eq(itemId));
    }

    @Test
    void buscarTodosPorCardapio_DeveRetornarStatusOkEListaItens() throws Exception {
        Long cardapioId = 10L;
        ItemCardapioOutputDTO outputMock = new ItemCardapioOutputDTO(
                1L,
                "Batata Frita",
                "Porção individual 300g",
                15.00,
                true,
                "foto",
                cardapioId
        );

        when(itemCardapioController.buscarTodosPorCardapio(HEADER_USUARIO_LOGADO_ID, cardapioId))
                .thenReturn(List.of(outputMock));

        mockMvc.perform(get("/itens-cardapio/cardapios/{cardapioId}", cardapioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemCardapioId").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Batata Frita"));

        verify(itemCardapioController).buscarTodosPorCardapio(HEADER_USUARIO_LOGADO_ID, cardapioId);
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() throws Exception {
        Long itemId = 1L;

        mockMvc.perform(delete("/itens-cardapio/{itemId}", itemId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(itemCardapioController).deletarPorId(HEADER_USUARIO_LOGADO_ID, itemId);
    }
}