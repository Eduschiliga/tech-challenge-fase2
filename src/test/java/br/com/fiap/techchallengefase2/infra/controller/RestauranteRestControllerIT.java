package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.RestauranteController;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;
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
class RestauranteRestControllerIT extends AbstractContainer {

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
    private RestauranteController restauranteController;

    private final Long HEADER_USUARIO_LOGADO_ID = 99L;
    private String tokenAutenticacao;

    @BeforeEach
    void setUp() {
        tokenAutenticacao = "Bearer " + tokenGateway.gerarToken(HEADER_USUARIO_LOGADO_ID);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Restaurante do Edu",
                "endereco", "Rua Principal, 123",
                "tipoCozinha", "Brasileira",
                "horarioFuncionamento", "18:00 - 23:00"
        );

        when(restauranteController.criar(eq(HEADER_USUARIO_LOGADO_ID), any(DadosRestauranteInputDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/restaurantes")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(restauranteController).criar(eq(HEADER_USUARIO_LOGADO_ID), any(DadosRestauranteInputDTO.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Restaurante do Edu Atualizado",
                "endereco", "Rua Secundária, 456",
                "tipoCozinha", "Italiana",
                "horarioFuncionamento", "19:00 - 00:00"
        );

        when(restauranteController.atualizar(eq(HEADER_USUARIO_LOGADO_ID), eq(1L), any(DadosRestauranteInputDTO.class)))
                .thenReturn(1L);

        mockMvc.perform(put("/restaurantes/{restauranteId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(restauranteController).atualizar(eq(HEADER_USUARIO_LOGADO_ID), eq(1L), any(DadosRestauranteInputDTO.class));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkERestaurante() throws Exception {
        RestauranteOutputDTO outputMock = new RestauranteOutputDTO(
                1L,
                "Restaurante do Edu",
                "Rua Principal, 123",
                "Brasileira",
                "18:00 - 23:00",
                HEADER_USUARIO_LOGADO_ID
        );

        when(restauranteController.buscarPorId(HEADER_USUARIO_LOGADO_ID, 1L)).thenReturn(outputMock);

        mockMvc.perform(get("/restaurantes/{restauranteId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante do Edu"));
    }

    @Test
    void buscarTodosPorUsuario_DeveRetornarStatusOkEListaRestaurantes() throws Exception {
        RestauranteOutputDTO outputMock = new RestauranteOutputDTO(
                1L,
                "Restaurante do Edu",
                "Rua Principal, 123",
                "Brasileira",
                "18:00 - 23:00",
                HEADER_USUARIO_LOGADO_ID
        );

        when(restauranteController.buscarTodosPorUsuarioId(HEADER_USUARIO_LOGADO_ID)).thenReturn(List.of(outputMock));

        mockMvc.perform(get("/restaurantes/usuario")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Restaurante do Edu"));
    }

    @Test
    void buscarTodos_DeveRetornarStatusOkEListaRestaurantes() throws Exception {
        RestauranteOutputDTO outputMock = new RestauranteOutputDTO(
                1L,
                "Restaurante do Edu",
                "Rua Principal, 123",
                "Brasileira",
                "18:00 - 23:00",
                HEADER_USUARIO_LOGADO_ID
        );

        when(restauranteController.buscarTodos()).thenReturn(List.of(outputMock));

        mockMvc.perform(get("/restaurantes")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Restaurante do Edu"));
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() throws Exception {
        mockMvc.perform(delete("/restaurantes/{restauranteId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(restauranteController).deletarPorId(HEADER_USUARIO_LOGADO_ID, 1L);
    }
}