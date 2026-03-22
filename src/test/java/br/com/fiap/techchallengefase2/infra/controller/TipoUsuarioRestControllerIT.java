package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.TipoUsuarioController;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.AtualizarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.CriarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.TipoUsuarioOutputDTO;
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
class TipoUsuarioRestControllerIT extends AbstractContainer {

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
    private TipoUsuarioController tipoUsuarioController;

    private final Long HEADER_USUARIO_LOGADO_ID = 99L;
    private String tokenAutenticacao;

    @BeforeEach
    void setUp() {
        tokenAutenticacao = "Bearer " + tokenGateway.gerarToken(HEADER_USUARIO_LOGADO_ID);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Administrador",
                "restauranteId", 10L
        );

        when(tipoUsuarioController.criar(eq(HEADER_USUARIO_LOGADO_ID), any(CriarTipoUsuarioInputDTO.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/tipos-usuario")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(tipoUsuarioController).criar(eq(HEADER_USUARIO_LOGADO_ID), any(CriarTipoUsuarioInputDTO.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Gerente"
        );

        Long tipoUsuarioId = 1L;

        when(tipoUsuarioController.atualizar(eq(HEADER_USUARIO_LOGADO_ID), any(AtualizarTipoUsuarioInputDTO.class)))
                .thenReturn(tipoUsuarioId);

        mockMvc.perform(put("/tipos-usuario/{tipoUsuarioId}", tipoUsuarioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(tipoUsuarioController).atualizar(eq(HEADER_USUARIO_LOGADO_ID), any(AtualizarTipoUsuarioInputDTO.class));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkETipoUsuario() throws Exception {
        Long tipoUsuarioId = 1L;
        TipoUsuarioOutputDTO outputMock = new TipoUsuarioOutputDTO(
                tipoUsuarioId,
                "Administrador",
                10L
        );

        when(tipoUsuarioController.buscarPorId(eq(HEADER_USUARIO_LOGADO_ID), eq(tipoUsuarioId))).thenReturn(outputMock);

        mockMvc.perform(get("/tipos-usuario/{tipoUsuarioId}", tipoUsuarioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoUsuarioId").value(tipoUsuarioId))
                .andExpect(jsonPath("$.nome").value("Administrador"))
                .andExpect(jsonPath("$.restauranteId").value(10L));

        verify(tipoUsuarioController).buscarPorId(eq(HEADER_USUARIO_LOGADO_ID), eq(tipoUsuarioId));
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarStatusOkEListaTiposUsuario() throws Exception {
        Long restauranteId = 10L;
        TipoUsuarioOutputDTO outputMock = new TipoUsuarioOutputDTO(
                1L,
                "Atendente",
                restauranteId
        );

        when(tipoUsuarioController.buscarTodosPorRestaurante(eq(HEADER_USUARIO_LOGADO_ID), eq(restauranteId)))
                .thenReturn(List.of(outputMock));

        mockMvc.perform(get("/tipos-usuario/restaurantes/{restauranteId}", restauranteId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoUsuarioId").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Atendente"))
                .andExpect(jsonPath("$[0].restauranteId").value(restauranteId));

        verify(tipoUsuarioController).buscarTodosPorRestaurante(eq(HEADER_USUARIO_LOGADO_ID), eq(restauranteId));
    }

    @Test
    void buscarTodosPorUsuario_DeveRetornarStatusOkEListaTiposUsuario() throws Exception {
        Long usuarioId = 5L;
        TipoUsuarioOutputDTO outputMock = new TipoUsuarioOutputDTO(
                1L,
                "Cliente",
                null
        );

        when(tipoUsuarioController.buscarTodosPorUsuario(eq(HEADER_USUARIO_LOGADO_ID), eq(usuarioId)))
                .thenReturn(List.of(outputMock));

        mockMvc.perform(get("/tipos-usuario/usuarios/{usuarioId}", usuarioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoUsuarioId").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Cliente"));

        verify(tipoUsuarioController).buscarTodosPorUsuario(eq(HEADER_USUARIO_LOGADO_ID), eq(usuarioId));
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() throws Exception {
        Long tipoUsuarioId = 1L;

        mockMvc.perform(delete("/tipos-usuario/{tipoUsuarioId}", tipoUsuarioId)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tipoUsuarioController).deletarTipoUsuario(eq(HEADER_USUARIO_LOGADO_ID), eq(tipoUsuarioId));
    }
}