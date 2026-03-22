package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.CardapioController;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
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
class CardapioRestControllerIT extends AbstractContainer {

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
    private CardapioController cardapioController;

    private final Long HEADER_USUARIO_LOGADO_ID = 99L;
    private String tokenAutenticacao;

    @BeforeEach
    void setUp() {
        tokenAutenticacao = "Bearer " + tokenGateway.gerarToken(HEADER_USUARIO_LOGADO_ID);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Cardápio Principal"
        );

        Long restauranteId = 10L;

        when(cardapioController.criar(eq(HEADER_USUARIO_LOGADO_ID), any(CriarCardapioInputDTO.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/cardapios/restaurantes/{restauranteId}", restauranteId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(cardapioController).criar(eq(HEADER_USUARIO_LOGADO_ID), any(CriarCardapioInputDTO.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Cardápio Atualizado"
        );

        Long cardapioId = 1L;

        when(cardapioController.atualizar(eq(HEADER_USUARIO_LOGADO_ID), any(AtualizarCardapioInputDTO.class)))
                .thenReturn(cardapioId);

        mockMvc.perform(put("/cardapios/{cardapioId}", cardapioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID) // <---
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(cardapioController).atualizar(eq(HEADER_USUARIO_LOGADO_ID), any(AtualizarCardapioInputDTO.class));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkECardapio() throws Exception {
        Long cardapioId = 1L;
        CardapioOutputDTO outputMock = new CardapioOutputDTO(
                cardapioId,
                null,
                List.of(),
                "Cardápio Principal"
        );

        when(cardapioController.buscarPorId(cardapioId)).thenReturn(outputMock);

        mockMvc.perform(get("/cardapios/{cardapioId}", cardapioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID) // <---
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardapioId").value(cardapioId)) // <--- Corrigido para $.id
                .andExpect(jsonPath("$.nome").value("Cardápio Principal"));

        verify(cardapioController).buscarPorId(cardapioId);
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarStatusOkEListaCardapios() throws Exception {
        Long restauranteId = 10L;
        CardapioOutputDTO outputMock = new CardapioOutputDTO(
                1L,
                null,
                List.of(),
                "Cardápio de Sobremesas"
        );

        when(cardapioController.buscarTodosPorRestaurante(HEADER_USUARIO_LOGADO_ID, restauranteId))
                .thenReturn(List.of(outputMock));

        mockMvc.perform(get("/cardapios/restaurantes/{restauranteId}", restauranteId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cardapioId").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Cardápio de Sobremesas"));

        verify(cardapioController).buscarTodosPorRestaurante(HEADER_USUARIO_LOGADO_ID, restauranteId);
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() throws Exception {
        Long cardapioId = 1L;

        mockMvc.perform(delete("/cardapios/{cardapioId}", cardapioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cardapioController).deletarPorId(HEADER_USUARIO_LOGADO_ID, cardapioId);
    }
}